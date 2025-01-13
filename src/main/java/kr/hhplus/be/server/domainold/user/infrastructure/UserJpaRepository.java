package kr.hhplus.be.server.domainold.user.infrastructure;

import kr.hhplus.be.server.domainold.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

}
