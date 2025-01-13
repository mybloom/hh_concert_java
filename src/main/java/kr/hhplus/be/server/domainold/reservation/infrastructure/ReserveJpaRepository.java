package kr.hhplus.be.server.domainold.reservation.infrastructure;

import java.util.Optional;
import kr.hhplus.be.server.domainold.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveJpaRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findBySeatId(long seatId);
}
