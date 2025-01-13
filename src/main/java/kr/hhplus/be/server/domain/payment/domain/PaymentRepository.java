package kr.hhplus.be.server.domain.payment.domain;

import java.util.Optional;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findByReservationId(long reservationId);
}
