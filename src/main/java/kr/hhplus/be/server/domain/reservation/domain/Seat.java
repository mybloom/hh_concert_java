package kr.hhplus.be.server.domain.reservation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.hhplus.be.server.common.config.database.BaseEntity;
import lombok.Getter;

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
}
