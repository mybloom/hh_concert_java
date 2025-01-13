package kr.hhplus.be.server.domainold.queuetoken.web.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {
    private long userId;
}
