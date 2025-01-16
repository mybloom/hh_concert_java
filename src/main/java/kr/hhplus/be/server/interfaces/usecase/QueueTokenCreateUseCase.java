package kr.hhplus.be.server.interfaces.usecase;

import kr.hhplus.be.server.domain.queuetoken.application.QueueTokenClearService;
import kr.hhplus.be.server.domain.queuetoken.application.QueueTokenCreateService;
import kr.hhplus.be.server.domain.queuetoken.application.ThresholdEvaluator;
import kr.hhplus.be.server.domain.queuetoken.application.result.QueueTokenResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueueTokenCreateUseCase {

    private final ThresholdEvaluator thresholdEvaluator;
    private final QueueTokenCreateService queueTokenCreateService;
    private final QueueTokenClearService queueTokenClearService;

    public QueueTokenResult create(final long userId) {
        //0. 사용자의 기존 토큰 존재시 제거
        queueTokenClearService.clearExistingToken(userId);

        //1. 대기열 임계 도달 확인
        boolean thresholdReached = thresholdEvaluator.isThresholdReached();

        //2. 토큰 생성
        final QueueTokenResult queueTokenResult;
        if (thresholdReached) {
            queueTokenResult = queueTokenCreateService.createWaitQueueToken(userId);
        } else {
            queueTokenResult = queueTokenCreateService.createActiveQueueToken(userId);
        }

        return queueTokenResult;
    }
}
