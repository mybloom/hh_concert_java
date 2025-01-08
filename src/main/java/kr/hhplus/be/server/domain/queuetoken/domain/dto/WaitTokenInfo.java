package kr.hhplus.be.server.domain.queuetoken.domain.dto;

import lombok.Getter;

@Getter
public class WaitTokenInfo {

    long waitOrder;

    public static WaitTokenInfo of(long waitOrder) {
        WaitTokenInfo waitTokenInfo = new WaitTokenInfo();
        waitTokenInfo.waitOrder = waitOrder;
        return waitTokenInfo;
    }
}
