package kr.hhplus.be.server.domain.reservation.application;

import static kr.hhplus.be.server.common.exception.errorcode.IllegalArgumentErrorCode.INVALID_SEAT_ID;

import java.util.List;
import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.domain.reservation.domain.Seat;
import kr.hhplus.be.server.domain.reservation.application.result.SeatResponse;
import kr.hhplus.be.server.domain.reservation.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public List<SeatResponse> retrieveAvailableSeats(long scheduleId) {
        List<Seat> seats = seatRepository.findAllByScheduleId(scheduleId);
        return seats.stream()
            .map(SeatResponse::of)
            .toList();
    }

    public long retrieveScheduleSeatPrice(long seatId) {
        Seat seat = seatRepository.findById(seatId)
            .orElseThrow(() -> new BusinessIllegalArgumentException(INVALID_SEAT_ID));
        return seat.getPrice();
    }
}
