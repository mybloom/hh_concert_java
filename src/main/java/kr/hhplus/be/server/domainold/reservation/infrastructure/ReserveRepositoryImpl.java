package kr.hhplus.be.server.domainold.reservation.infrastructure;

import java.util.Optional;
import kr.hhplus.be.server.domainold.reservation.domain.Reservation;
import kr.hhplus.be.server.domainold.reservation.domain.ReserveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReserveRepositoryImpl implements ReserveRepository {

    private final ReserveJpaRepository reserveJpaRepository;

    @Override
    public Optional<Reservation> findBySeatId(long seatId) {
        return reserveJpaRepository.findBySeatId(seatId);
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reserveJpaRepository.save(reservation);
    }

    @Override
    public Optional<Reservation> findById(long reservationId) {
        return reserveJpaRepository.findById(reservationId);
    }
}
