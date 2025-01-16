package kr.hhplus.be.server.interfaces.queuetoken.dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenRequest {
    @NotNull
    private long userId;
}
