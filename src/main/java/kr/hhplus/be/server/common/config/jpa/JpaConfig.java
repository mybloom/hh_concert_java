package kr.hhplus.be.server.common.config.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {
    "kr.hhplus.be.server.domain.point.infrastructure",
    "kr.hhplus.be.server.domain.queuetoken.infrastructure",
    "kr.hhplus.be.server.domain.user.infrastructure",
    "kr.hhplus.be.server.domain.concert.infrastructure",
    "kr.hhplus.be.server.domain.reservation.infrastructure"
})
public class JpaConfig {

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }
}