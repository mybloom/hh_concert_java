package kr.hhplus.be.server.domain.reservation.domain;

import static kr.hhplus.be.server.common.exception.IllegalArgumentErrorCode.INVALID_SEAT_ID;

import java.util.List;
import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.domain.reservation.domain.dto.ReservationRequest;
import kr.hhplus.be.server.domain.reservation.domain.dto.ReservationResponse;
import kr.hhplus.be.server.domain.reservation.domain.dto.SeatResponse;
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


}
