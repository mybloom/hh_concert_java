package kr.hhplus.be.server.domain.queuetoken.model;

import static kr.hhplus.be.server.common.exception.errorcode.IllegalStateErrorCode.UNVERIFIED_TOKEN;
import static kr.hhplus.be.server.common.exception.errorcode.IllegalStateErrorCode.UNVERIFIED_TOKEN_STATUS;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import kr.hhplus.be.server.common.config.database.BaseEntity;
import kr.hhplus.be.server.common.exception.BusinessIllegalStateException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
public class QueueToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String tokenUuid;

    @Column(unique = true)
    private Long userId;

    @Enumerated(EnumType.STRING)
    private QueueTokenStatus status;

    private LocalDateTime runningExpiredAt;

    //    @Transient
    private Long waitOffset;

    public static QueueToken createWaitToken(Long userId, String tokenUuid) {
        return QueueToken.builder()
            .userId(userId)
            .status(QueueTokenStatus.WAIT)
            .tokenUuid(tokenUuid)
            .build();
    }

    public static QueueToken createActiveToken(
        Long userId, String tokenUuid, int initExpirationMinutes
    ) {
        return QueueToken.builder()
            .userId(userId)
            .status(QueueTokenStatus.ACTIVE)
            .tokenUuid(tokenUuid)
            .runningExpiredAt(generateRunningExpiredAt(initExpirationMinutes))
            .build();
    }

    private static LocalDateTime generateRunningExpiredAt(int initExpirationMinutes) {
        return LocalDateTime.now().plusMinutes(initExpirationMinutes);
    }

    public void updateWaitOffsetWith(long lastActiveOffset) {
        if (id == null) {
            throw new BusinessIllegalStateException(UNVERIFIED_TOKEN);
        }

        this.waitOffset = this.id - lastActiveOffset;
    }

    public boolean isExpired(int paymentExpirationMinutes) {
        //만료 조건 2가지(OR 조건)
        //1. status가 INVALID인 경우
        if (status.equals(QueueTokenStatus.INVALID)) {
            return true;
        }

        //2. status가 ACTIVE이고, expirationProcessingTime이 지났을 경우
        return isExpirationTimeReached(paymentExpirationMinutes);
    }

    public void invalidate() {
        if (!status.equals(QueueTokenStatus.ACTIVE)) {
            throw new BusinessIllegalStateException(UNVERIFIED_TOKEN_STATUS);
        }
        status = QueueTokenStatus.INVALID;
    }

    public boolean isExpirationTimeReached(int paymentExpirationMinutes) {
        LocalDateTime expirationProcessingTime = runningExpiredAt.plusMinutes(paymentExpirationMinutes);
        boolean isAfter = LocalDateTime.now().isAfter(expirationProcessingTime);

        return isAfter;
    }

}
