package kr.hhplus.be.server.domain.queuetoken.domain;

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
        // todo: 매번 active 토큰수를 확인해야 할까? 캐싱 필요해 보인다.
        long activeTokenCounts = queueTokenRepository.countByStatus(QueueTokenStatus.ACTIVE);
        boolean hasExceededLimit = QueueToken.isExceededLimit(queueTokenProperties.getThreshold(), activeTokenCounts);

        // 2. 토큰을 상태에 맞게 생성한다.
        QueueToken queueToken;
        if (hasExceededLimit) {
            long waitTokenCounts = queueTokenRepository.countByStatus(QueueTokenStatus.WAIT);
            queueToken = QueueToken.createWaitToken(user, waitTokenCounts + 1);
        } else {
            queueToken = QueueToken.createActiveToken(user);
        }

        // 3. 생성된 토큰을 DB에 저장한다.
        queueToken = queueTokenRepository.save(queueToken);

        // 4. 생성된 토큰을 반환한다.
        if (hasExceededLimit) {
            return QueueTokenResponse.waitQueueTokenResponse(queueToken, WaitTokenInfo.of(queueToken.getWaitOrder()));
        } else {
            return QueueTokenResponse.activeQueueTokenResponse(queueToken);
        }
    }

}
