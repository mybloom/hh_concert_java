package kr.hhplus.be.server.domain.token.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private String queueStatus;
    private int waitPosition;
}
