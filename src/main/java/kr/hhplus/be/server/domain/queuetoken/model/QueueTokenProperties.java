package kr.hhplus.be.server.domain.queuetoken.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@ConfigurationProperties(prefix = "server.queue")
@Configuration
public class QueueTokenProperties {
    private int threshold;
    private int initExpirationMinutes;   // active 상태 초기 부여 만료 시간 (분)
    private int paymentExpirationMinutes; // 결제 대기 상태 만료 시간 (분)
}
