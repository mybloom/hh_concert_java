package kr.hhplus.be.server.domain.reservation.domain;

import java.util.Optional;

public interface ReserveRepository {

    Optional<Reservation> findBySeatId(long seatId);

    Reservation save(Reservation reservation);
}
