package kr.hhplus.be.server.domainold.reservation.domain;

import java.util.List;
import kr.hhplus.be.server.domainold.reservation.domain.dto.SeatResponse;
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
