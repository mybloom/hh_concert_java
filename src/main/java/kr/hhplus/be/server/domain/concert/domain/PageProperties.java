package kr.hhplus.be.server.domain.concert.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@ConfigurationProperties(prefix = "page")
@Configuration
public class PageProperties {
    private int size;
}
