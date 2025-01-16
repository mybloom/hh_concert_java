package kr.hhplus.be.server.domain.reservation.repository;

import java.util.Optional;
import kr.hhplus.be.server.domain.reservation.domain.Reservation;

public interface ReservationRepository {

    Optional<Reservation> findBySeatId(long seatId);

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(long reservationId);
}
