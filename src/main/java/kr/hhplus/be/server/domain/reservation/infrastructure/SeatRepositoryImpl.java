package kr.hhplus.be.server.domain.reservation.infrastructure;

import java.util.List;
import java.util.Optional;
import kr.hhplus.be.server.domain.reservation.domain.Seat;
import kr.hhplus.be.server.domain.reservation.repository.SeatRepository;
import kr.hhplus.be.server.domain.reservation.domain.SeatReserveStatus;
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
    public Optional<Seat> findByIdWithLock(long seatId) {
        return seatJpaRepository.findByIdWithLock(seatId);
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
