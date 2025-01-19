package kr.hhplus.be.server.domain.reservation.repository;

import java.util.Optional;
import kr.hhplus.be.server.domain.reservation.domain.Payment;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findByReservationId(long reservationId);
}
