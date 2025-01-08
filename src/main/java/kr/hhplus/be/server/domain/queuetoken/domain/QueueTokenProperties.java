package kr.hhplus.be.server.domain.queuetoken.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@ConfigurationProperties(prefix = "server.token")
@Configuration
public class QueueTokenProperties {
    private int threshold;
}
