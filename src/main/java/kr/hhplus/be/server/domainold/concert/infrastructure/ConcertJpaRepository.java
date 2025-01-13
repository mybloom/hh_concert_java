package kr.hhplus.be.server.domainold.concert.infrastructure;

import kr.hhplus.be.server.domainold.concert.domain.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {

}
