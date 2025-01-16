package kr.hhplus.be.server.domain.queuetoken.application.result;

import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenStatus;
import lombok.Getter;

@Getter
public class QueueTokenResult {

    private String tokenUuid;
    private QueueTokenStatus status;
    private WaitQueueTokenResult waitTokenInfo;


    public static QueueTokenResult activeQueueTokenResponse(String tokenUuid, QueueTokenStatus tokenStatus) {
        QueueTokenResult response = new QueueTokenResult();
        response.tokenUuid = tokenUuid;
        response.status = tokenStatus;

        return response;
    }

    public static QueueTokenResult waitQueueTokenResponse(String tokenUuid, QueueTokenStatus tokenStatus, WaitQueueTokenResult waitTokenInfo) {
        QueueTokenResult response = new QueueTokenResult();
        response.tokenUuid = tokenUuid;
        response.status = tokenStatus;
        response.waitTokenInfo = waitTokenInfo;

        return response;
    }
}
