package kr.hhplus.be.server.domain.queuetoken.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private String queueStatus;
    private int waitPosition;
}
