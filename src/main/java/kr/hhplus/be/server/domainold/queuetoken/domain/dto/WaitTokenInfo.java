package kr.hhplus.be.server.domainold.queuetoken.domain.dto;

import lombok.Getter;

@Getter
public class WaitTokenInfo {

    long waitOffset;

    public static WaitTokenInfo of(long waitOffset) {
        WaitTokenInfo waitTokenInfo = new WaitTokenInfo();
        waitTokenInfo.waitOffset = waitOffset;
        return waitTokenInfo;
    }
}
