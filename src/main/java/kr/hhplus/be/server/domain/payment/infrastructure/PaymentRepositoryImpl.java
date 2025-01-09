package kr.hhplus.be.server.domain.payment.infrastructure;

import java.util.Optional;
import kr.hhplus.be.server.domain.payment.domain.Payment;
import kr.hhplus.be.server.domain.payment.domain.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;

    @Override
    public Payment save(Payment payment) {
        return paymentJpaRepository.save(payment);
    }

    @Override
    public Optional<Payment> findByReservationId(long reservationId) {
        return paymentJpaRepository.findByReservationId(reservationId);
    }
}
