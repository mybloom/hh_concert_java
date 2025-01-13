package kr.hhplus.be.server.domainold.reservation.infrastructure;

import java.util.List;
import java.util.Optional;
import kr.hhplus.be.server.domainold.reservation.domain.Seat;
import kr.hhplus.be.server.domainold.reservation.domain.SeatRepository;
import kr.hhplus.be.server.domainold.reservation.domain.SeatReserveStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class SeatRepositoryImpl implements SeatRepository {

    private final SeatJpaRepository seatJpaRepository;

    @Override
    public List<Seat> findAllByScheduleId(long scheduleId) {
        return seatJpaRepository.findAllByScheduleId(scheduleId);
    }

    @Override
    public Optional<Seat> findById(long seatId) {
        return seatJpaRepository.findById(seatId);
    }

    @Override
    public Seat save(Seat seat) {
        return seatJpaRepository.save(seat);
    }

    @Override
    public int updateSeatStatus(Long seatId, SeatReserveStatus status) {
        return seatJpaRepository.updateSeatStatus(seatId, status);
    }
}
