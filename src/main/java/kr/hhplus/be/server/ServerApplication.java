package kr.hhplus.be.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
	"kr.hhplus.be.server.domain.point.infrastructure",
	"kr.hhplus.be.server.domain.queuetoken.infrastructure",
	"kr.hhplus.be.server.domain.user.infrastructure",
	"kr.hhplus.be.server.domain.concert.infrastructure",
	"kr.hhplus.be.server.domain.reservation.infrastructure",
	"kr.hhplus.be.server.domain.payment.infrastructure"
})

public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
