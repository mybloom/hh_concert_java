package kr.hhplus.be.server.domain.concert.infrastructure;

import kr.hhplus.be.server.domain.concert.domain.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertJpaRepository extends JpaRepository<Concert, Long> {

}
