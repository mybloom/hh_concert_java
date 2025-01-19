package kr.hhplus.be.server.domain.reservation.infrastructure;

import java.util.List;
import java.util.Optional;
import kr.hhplus.be.server.domain.reservation.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReserveJpaRepository extends JpaRepository<Reservation, Long> {

    Optional<Reservation> findBySeatId(long seatId);


    List<Reservation> findAllById(long seatId);
}
