package kr.hhplus.be.server.domainold.queuetoken.domain.dto;

import kr.hhplus.be.server.domainold.queuetoken.domain.QueueToken;
import kr.hhplus.be.server.domainold.queuetoken.domain.QueueTokenStatus;
import lombok.Getter;

@Getter
public class QueueTokenResponse {

    private String tokenUuid;
    private QueueTokenStatus status;
    private WaitTokenInfo waitTokenInfo;


    public static QueueTokenResponse activeQueueTokenResponse(QueueToken queueToken) {
        QueueTokenResponse response = new QueueTokenResponse();
        response.tokenUuid = queueToken.getTokenUuid();
        response.status = queueToken.getStatus();
        return response;
    }

    public static QueueTokenResponse waitQueueTokenResponse(QueueToken queueToken, WaitTokenInfo waitTokenInfo) {
        QueueTokenResponse response = new QueueTokenResponse();
        response.tokenUuid = queueToken.getTokenUuid();
        response.status = queueToken.getStatus();
        response.waitTokenInfo = waitTokenInfo;
        return response;
    }
}
