package kr.hhplus.be.server.domain.point.model;

import static kr.hhplus.be.server.common.exception.IllegalArgumentErrorCode.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.Setter;


@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Entity
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long balance;

    @Setter
    @Column(unique = true)
    private long userId;

    public static Point createInitBalance(long userId) {
        return Point.builder()
            .userId(userId)
            .balance(0)
            .build();
    }

    public long charge(long amount) {
        balance += amount;
        return balance;
    }

    public long use(long amount) {
        if (amount <= 0) {
            throw new BusinessIllegalArgumentException(INVALID_USE_AMOUNT);
        }

        if (balance < amount) {
            throw new BusinessIllegalArgumentException(INSUFFICIENT_BALANCE);
        }

        balance -= amount;
        return balance;
    }
}
