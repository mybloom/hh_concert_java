package kr.hhplus.be.server.domain.queuetoken.infrastructure;

import java.util.Optional;
import kr.hhplus.be.server.domain.queuetoken.domain.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.domain.QueueTokenStatus;
import kr.hhplus.be.server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueTokenJpaRepository extends JpaRepository<QueueToken, Long> {

    long countByStatus(QueueTokenStatus status);

    Optional<QueueToken> findByUser(User user);
}
