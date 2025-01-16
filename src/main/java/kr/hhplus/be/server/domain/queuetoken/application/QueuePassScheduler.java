package kr.hhplus.be.server.domain.queuetoken.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import kr.hhplus.be.server.domain.queuetoken.model.QueueOffset;
import kr.hhplus.be.server.domain.queuetoken.model.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenProperties;
import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenStatus;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueOffsetRepository;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueuePassScheduler {

    private static final long QUEUE_OFFSET_INDEX = 1L;

    private final ThresholdEvaluator thresholdEvaluator;

    private final QueueTokenRepository queueTokenRepository;
    private final QueueTokenProperties queueTokenProperties;
    private final QueueOffsetRepository queueOffsetRepository;

    @Transactional
    @Scheduled(fixedRate = 1000000) //10초마다 실행(밀리초 단위) (fixedRate = 10000) //10초
    public void passQueue() {
        log.info("QueuePassScheduler passQueue start!");
        //서버 임계치 도달 여부 확인하여 분리해서 처리
        boolean thresholdReached = thresholdEvaluator.isThresholdReached();

        if (thresholdReached) {
            log.info("Queue is exceeded");
            processAfterThreshold();
            log.info("QueuePassScheduler processAfterThreshold() end!");
        } else {
            log.info("Queue is not exceeded");
            processBeforeThreshold();
            log.info("QueuePassScheduler processBeforeThreshold end!");
        }
    }

    public void processAfterThreshold() {
        //1. 만료 토큰 제거
        long expirationTokenCount = removeInvalidToken();
        //만료 토큰이 없다면 task 종료
        if (expirationTokenCount == 0) {
            return;
        }

        //2. 기존의 lastActiveOffset 구하기 (쓰기락)
        Long beforeLastActiveOffset = queueOffsetRepository.findByIdWithLock(QUEUE_OFFSET_INDEX)
            .map(QueueOffset::getLastActiveOffset)
            .or(() -> queueTokenRepository.findTopByStatusOrderByIdDesc(QueueTokenStatus.ACTIVE)
                .map(QueueToken::getId))
            .orElse(1L); //active 상태가 없다면 기본 1로 설정 (불가능한 케이스)

        //2-2. lastActiveOffset 업데이트
        Optional<QueueToken> queueTokenWithMaxId = queueTokenRepository.findQueueTokenWithMaxId();  // 최대 id를 가진 QueueToken 조회
        // 만료된 수보다 기다리는 수가 더 적을 경우 대비 : 대기열의 max Id를 비교하여 더 작은 값을 lastActiveOffset 으로 설정
        final long lastActiveOffset = Math.min(
            (beforeLastActiveOffset + expirationTokenCount),
            queueTokenWithMaxId.map(QueueToken::getId).orElse(Long.MAX_VALUE)
        );

        //3.WAIT 상태의 토큰을 ACTIVE 상태로 변경.
        //(beforeLastActiveOffset +1 ~ lastActiveOffset) 까지의 WAIT 상태 토큰을 ACTIVE 로 변경. waitOffset = 0 처리
        long activeRangeStart = beforeLastActiveOffset + 1;
        for (long id = activeRangeStart; id <= lastActiveOffset; id++) {
            int initExpirationMinutes = queueTokenProperties.getInitExpirationMinutes();
            queueTokenRepository.updateTokenStatusAndOffset(
                id, QueueTokenStatus.ACTIVE, 0L, LocalDateTime.now().plusMinutes(initExpirationMinutes)
            );
        }
        log.info("Updated tokens from ID {} to {}", activeRangeStart, lastActiveOffset);

        //3-2 만료 토큰 수가 많으면 스케줄러 종료
        final long expiredTokenCount = expirationTokenCount;
        final long waitingTokenCount = queueTokenWithMaxId.map(QueueToken::getId).orElse(Long.MAX_VALUE) - beforeLastActiveOffset;
        final boolean isExpiredTokenMore = expiredTokenCount > waitingTokenCount;
        if (isExpiredTokenMore) {
            log.info(
                "ExpiredTokenMore! - lastActiveOffset: {}, expiredTokenCount: {}, waitingTokenCount: {}, isExpiredTokenMore: {}",
                lastActiveOffset, expiredTokenCount, waitingTokenCount, isExpiredTokenMore);
            return;
        }

        //4. 가장 마지막 ACTIVE 토큰의 PK로 QueueOffset 의 lastActiveOffset 업데이트 (동시성처리)
        queueOffsetRepository.updateLastActiveOffset(QUEUE_OFFSET_INDEX, lastActiveOffset);

        //5. WAIT 상태 토큰 waitOffset 감소: lastActiveOffset 이후의 WAIT 상태 토큰 waitOffset 감소 //데이터 정합성이랑 관련없을 듯 해서 별도의 트랜잭션으로 처리
        List<QueueToken> waitTokens = queueTokenRepository.findByIdGreaterThanEqual(lastActiveOffset + 1);
        waitTokens.stream()
            .forEach(queueToken -> queueTokenRepository.updateTokenStatusAndOffset(
                queueToken.getId(),
                QueueTokenStatus.WAIT,
                queueToken.getId() - lastActiveOffset)
            );
    }

    @Transactional
    public void processBeforeThreshold() {
        //1. 만료 토큰 제거
        //queueOffset 은 lock 걸지 않는다. 조회도 필요없음. 이건 새로운 토큰이 대기열 들어오는 다른 스레드에서 update가 될 것임
        removeInvalidToken();
    }

    long removeInvalidToken() {
        AtomicLong expirationTokenCount = new AtomicLong(); //Variable used in lambda expression should be final or effectively final

        int paymentExpirationMinutes = queueTokenProperties.getPaymentExpirationMinutes();
        queueTokenRepository.findAllByStatus(QueueTokenStatus.INVALID)
            .forEach(queueToken -> {
                if (queueToken.isExpired(paymentExpirationMinutes)) {
                    queueTokenRepository.delete(queueToken);
                    expirationTokenCount.getAndIncrement();
                }
            });
        queueTokenRepository.findAllByStatus(QueueTokenStatus.ACTIVE)
            .forEach(queueToken -> {
                if (queueToken.isExpired(paymentExpirationMinutes)) {
                    queueTokenRepository.delete(queueToken);
                    expirationTokenCount.getAndIncrement();
                }
            });

        return expirationTokenCount.get();
    }

}
