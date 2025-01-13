package kr.hhplus.be.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {
	"kr.hhplus.be.server.domainold.point.infrastructure",
	"kr.hhplus.be.server.domainold.queuetoken.infrastructure",
	"kr.hhplus.be.server.domainold.user.infrastructure",
	"kr.hhplus.be.server.domainold.concert.infrastructure",
	"kr.hhplus.be.server.domainold.reservation.infrastructure",
	"kr.hhplus.be.server.domainold.payment.infrastructure"
})

public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
