package kr.hhplus.be.server.domain.queuetoken.domain;

import static kr.hhplus.be.server.common.exception.IllegalArgumentErrorCode.QUEUE_TOKEN_NOT_FOUND;

import java.util.List;
import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.domain.queuetoken.domain.dto.QueueTokenResponse;
import kr.hhplus.be.server.domain.queuetoken.domain.dto.WaitTokenInfo;
import kr.hhplus.be.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueueTokenService {

    private final QueueTokenProperties queueTokenProperties;
    private final QueueTokenRepository queueTokenRepository;

    public QueueTokenResponse create(User user) {
        //0. 기존 토큰이 있다면 제거한다.
        queueTokenRepository.findByUser(user).ifPresent(queueTokenRepository::delete);

        // 1. 임계치 도달을 확인한다.
        //todo: 매번 active 토큰수를 확인해야 할까? 캐싱 필요해 보인다.
        boolean hasExceededLimit = isActiveTokenCountExceeded();

        // 2. 토큰을 상태에 맞게 생성한다.
        QueueToken queueToken;
        if (hasExceededLimit) {
            //wait offset 구하기
            QueueToken queueTokenWithMaxId =
                queueTokenRepository.findQueueTokenWithMaxId()
                    .orElseThrow(() -> new BusinessIllegalArgumentException(QUEUE_TOKEN_NOT_FOUND)); //todo: 에러 발생하면 이상한 부분인 것.
            long waitOffset = queueTokenWithMaxId.getWaitOffset() + 1;

            queueToken = QueueToken.createWaitToken(user, waitOffset);
        } else {
            queueToken = QueueToken.createActiveToken(user);
        }

        // 3. 생성된 토큰을 DB에 저장한다.
        queueToken = queueTokenRepository.save(queueToken);

        // 4. 생성된 토큰을 반환한다.
        if (hasExceededLimit) {
            return QueueTokenResponse.waitQueueTokenResponse(queueToken,
                WaitTokenInfo.of(queueToken.getWaitOffset()));
        } else {
            return QueueTokenResponse.activeQueueTokenResponse(queueToken);
        }
    }

    // 임계치 도달을 확인
    public boolean isActiveTokenCountExceeded() {
        long runningTokenCounts = queueTokenRepository.countByStatusIn(List.of(QueueTokenStatus.ACTIVE, QueueTokenStatus.INVALID));
        int threshold = queueTokenProperties.getThreshold();

        if (runningTokenCounts >= threshold) {
            return true;
        }
        return false;
    }

    public boolean isValidToken(String tokenUuid) {
        QueueToken queueToken = queueTokenRepository.findByTokenUuid(tokenUuid)
            .orElseThrow(() -> new BusinessIllegalArgumentException(QUEUE_TOKEN_NOT_FOUND));

        int paymentExpirationMinutes = queueTokenProperties.getPaymentExpirationMinutes();

        boolean expired = queueToken.isExpired(paymentExpirationMinutes);

        if(expired) {
            return false;
        }
        return true;
    }

}

