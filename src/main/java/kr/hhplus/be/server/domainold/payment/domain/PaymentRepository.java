package kr.hhplus.be.server.domainold.payment.domain;

import java.util.Optional;

public interface PaymentRepository {

    Payment save(Payment payment);

    Optional<Payment> findByReservationId(long reservationId);
}
