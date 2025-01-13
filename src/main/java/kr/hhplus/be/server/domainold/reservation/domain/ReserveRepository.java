package kr.hhplus.be.server.domainold.reservation.domain;

import java.util.Optional;

public interface ReserveRepository {

    Optional<Reservation> findBySeatId(long seatId);

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(long reservationId);
}
