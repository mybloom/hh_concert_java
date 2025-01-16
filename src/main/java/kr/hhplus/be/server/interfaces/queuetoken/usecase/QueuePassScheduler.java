package kr.hhplus.be.server.interfaces.queuetoken.usecase;

import kr.hhplus.be.server.domain.queuetoken.application.QueueOffsetService;
import kr.hhplus.be.server.domain.queuetoken.application.QueueTokenClearService;
import kr.hhplus.be.server.domain.queuetoken.application.QueueTokenModifyService;
import kr.hhplus.be.server.domain.queuetoken.application.ThresholdEvaluator;
import kr.hhplus.be.server.domain.queuetoken.model.QueueOffset;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class QueuePassScheduler {

    private final ThresholdEvaluator thresholdEvaluator;
    private final QueueTokenClearService queueTokenClearService;
    private final QueueOffsetService queueOffsetService;
    private final QueueTokenModifyService queueTokenModifyService;


    @Transactional
    @Scheduled(fixedRate = 1000000) //15분 (fixedRate = 10000) //10초
    public void passQueue() {
        //서버 임계치 도달 여부 확인하여 분리해서 처리
        boolean thresholdReached = thresholdEvaluator.isThresholdReached();

        log.info("QueuePassScheduler passQueue start!thresholdReached:{}", thresholdReached);
        if (thresholdReached) {
            processAfterThreshold();
        } else {
            processBeforeThreshold();
        }

        log.info("QueuePassScheduler processAfterThreshold() end!");
    }

    public void processBeforeThreshold() {
        //만료 토큰 제거
        queueTokenClearService.clearAllInvalidTokens();
    }

    public void processAfterThreshold() {
        //1. 만료 토큰 제거
        long invalidTokenCount = queueTokenClearService.clearAllInvalidTokens();

        //2. 기존의 lastActiveOffset 구하기.(임계치 넘었으므로 동시성 이슈 없음)
        QueueOffset queueOffset = queueOffsetService.retrieveQueueOffset();

        //3. WAIT 상태의 토큰을 ACTIVE 상태로 변경.
        long lastActiveOffset = queueTokenModifyService.activateToken(queueOffset.getLastActiveOffset(), invalidTokenCount);

        //4. 가장 마지막 ACTIVE 토큰의 PK로 QueueOffset 의 lastActiveOffset 업데이트
        queueOffsetService.updateLastActiveOffset(lastActiveOffset);
    }
}
