package kr.hhplus.be.server.domain.queuetoken.infrastructure;

import kr.hhplus.be.server.domain.queuetoken.domain.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.domain.QueueTokenStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueueTokenJpaRepository extends JpaRepository<QueueToken, Long> {

    long countByStatus(QueueTokenStatus status);

}
