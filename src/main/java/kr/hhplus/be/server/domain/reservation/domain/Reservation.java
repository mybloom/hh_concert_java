package kr.hhplus.be.server.domain.reservation.domain;

import static kr.hhplus.be.server.common.exception.errorcode.IllegalArgumentErrorCode.INVALID_RESERVATION_TIMEOUT;
import static kr.hhplus.be.server.common.exception.errorcode.IllegalStateErrorCode.UNVERIFIED_RESERVATION;
import static kr.hhplus.be.server.common.exception.errorcode.IllegalStateErrorCode.UNVERIFIED_RESERVATION_STATUS;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import kr.hhplus.be.server.common.config.database.BaseEntity;
import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.common.exception.BusinessIllegalStateException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Entity
public class Reservation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long seatId;

    private long userId;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    //todo: createAt이 5분 지난 것은 softDelete , Seat.status = AVAILABLE로 변경. (스케쥴러)
    public static Reservation createTempReservation(long seatId, long userId) {
        return Reservation.builder()
            .seatId(seatId)
            .userId(userId)
            .status(ReservationStatus.TEMP)
            .build();
    }

    public void validateReservationTime() {
        LocalDateTime now = LocalDateTime.now();
        long minutesDifference = ChronoUnit.MINUTES.between(this.getCreatedAt(), now);
        if (minutesDifference > 5) {
            throw new BusinessIllegalArgumentException(INVALID_RESERVATION_TIMEOUT);
        }
    }

    public void assignReservedStatus() {
        if(id == null){
            throw new BusinessIllegalStateException(UNVERIFIED_RESERVATION);
        }
        if(status != ReservationStatus.TEMP){
            throw new BusinessIllegalStateException(UNVERIFIED_RESERVATION_STATUS);
        }

        this.status = ReservationStatus.RESERVED;
    }

}
