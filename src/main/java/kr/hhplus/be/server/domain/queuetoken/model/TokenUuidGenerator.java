package kr.hhplus.be.server.domain.queuetoken.model;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TokenUuidGenerator {

    public String generateTokenUuid() {
        return UUID.randomUUID().toString();
    }
}
