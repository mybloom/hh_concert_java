package kr.hhplus.be.server.domain.reservation.infrastructure;

import java.util.Optional;
import kr.hhplus.be.server.domain.reservation.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByReservationId(long reservationId);
}
