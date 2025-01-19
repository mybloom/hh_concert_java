package kr.hhplus.be.server.domain.queuetoken.application;

import kr.hhplus.be.server.domain.queuetoken.application.result.WaitQueueTokenResult;
import kr.hhplus.be.server.domain.queuetoken.model.QueueOffset;
import kr.hhplus.be.server.domain.queuetoken.model.QueueToken;

public class WaitOffsetCalculator {

    public static WaitQueueTokenResult calculate(QueueToken queueToken, QueueOffset queueOffset) {
        queueToken.updateWaitOffsetWith(queueOffset.getLastActiveOffset());

        return new WaitQueueTokenResult(queueToken.getWaitOffset());
    }
}
