package kr.hhplus.be.server.domain.queuetoken.application;

import java.util.List;
import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenProperties;
import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenStatus;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ThresholdEvaluator {

    private final QueueTokenProperties queueTokenProperties;
    private final QueueTokenRepository queueTokenRepository;

    public boolean isThresholdReached() {
        //서버 임계 판단 기준 : active, invalid 상태 토큰은 "active pool"에 들어간 것으로 간주한다.
        long activePoolSize = queueTokenRepository.countByStatusIn(
            List.of(
                QueueTokenStatus.ACTIVE
                , QueueTokenStatus.INVALID
            )
        );
        int threshold = queueTokenProperties.getThreshold();

        return activePoolSize >= threshold;
    }
}
