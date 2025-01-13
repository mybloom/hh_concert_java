package kr.hhplus.be.server.domain.queuetoken.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QueueTokenStatus {

    WAIT("WAIT","대기토큰"),
    ACTIVE("ACTIVE","활성토큰"),
    INVALID("INVALID", "만료토큰");

    private final String value;
    private final String description;

}
