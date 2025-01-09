package kr.hhplus.be.server.domain.reservation.infrastructure;

import java.util.List;
import kr.hhplus.be.server.domain.reservation.domain.Seat;
import kr.hhplus.be.server.domain.reservation.domain.SeatRepository;
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
}
