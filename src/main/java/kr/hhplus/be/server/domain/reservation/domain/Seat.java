package kr.hhplus.be.server.domain.reservation.domain;

import static kr.hhplus.be.server.common.exception.IllegalArgumentErrorCode.SEAT_ALREADY_RESERVED;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.hhplus.be.server.common.config.database.BaseEntity;
import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
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
public class Seat extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long scheduleId;

    private long seatNo;

    private long price;

    @Enumerated(EnumType.STRING)
    private SeatReserveStatus status;

    public void occupied() {
        if (status == SeatReserveStatus.OCCUPIED) {
            throw new BusinessIllegalArgumentException(SEAT_ALREADY_RESERVED);
        }
        status = SeatReserveStatus.OCCUPIED;
    }
}
