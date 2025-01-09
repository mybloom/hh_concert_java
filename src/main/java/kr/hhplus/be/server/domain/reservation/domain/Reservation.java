package kr.hhplus.be.server.domain.reservation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.hhplus.be.server.common.config.database.BaseEntity;
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
    private long id;

    private long seatId;

    private long userId;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    public static Reservation createReservation(long seatId, long userId) {
        return Reservation.builder()
            .seatId(seatId)
            .userId(userId)
            .status(ReservationStatus.RESERVED)
            .build();
    }

    //todo: createAt이 5분 지난 것은 softDelete , Seat.status = AVAILABLE로 변경. (스케쥴러)
    public static Reservation createTempReservation(long seatId, long userId) {
        return Reservation.builder()
            .seatId(seatId)
            .userId(userId)
            .status(ReservationStatus.TEMP)
            .build();
    }

}
