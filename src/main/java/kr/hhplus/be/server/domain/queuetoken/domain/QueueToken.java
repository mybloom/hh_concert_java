package kr.hhplus.be.server.domain.queuetoken.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import kr.hhplus.be.server.common.config.database.BaseEntity;
import kr.hhplus.be.server.domain.user.domain.User;
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
    private long id;

    @Column(unique = true)
    private String tokenUuid;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private QueueTokenStatus status;

    private LocalDateTime runningExpiredAt;

    @Column(columnDefinition = "BIGINT DEFAULT 0")
    private Long waitOffset;

    public static QueueToken createWaitToken(User user, Long waitOffset) {
        return QueueToken.builder()
            .user(user)
            .status(QueueTokenStatus.WAIT)
            .tokenUuid(generateUuid())
            .waitOffset(waitOffset)
            .build();
    }

    public static QueueToken createActiveToken(User user) {
        return QueueToken.builder()
            .user(user)
            .status(QueueTokenStatus.ACTIVE)
            .tokenUuid(generateUuid())
            .runningExpiredAt(generateRunningExpiredAt())
            .build();
    }

    private static String generateUuid() {
        return UUID.randomUUID().toString();
    }

    private static LocalDateTime generateRunningExpiredAt() {
        return LocalDateTime.now().plusMinutes(5); //todo: properties에서 가져오기
    }

    public boolean isExpired(int paymentExpirationMinutes) {
        //만료 조건 2가지
        //1. status가 INVALID인 경우
        if (status.equals(QueueTokenStatus.INVALID)) {
            return true;
        }

        //2. expirationProcessingTime이 지났을 경우 : runningExpiredAt + paymentExpirationMinutes
        LocalDateTime expirationProcessingTime =
            runningExpiredAt.plusMinutes(paymentExpirationMinutes);
        boolean isAfter = LocalDateTime.now().isAfter(expirationProcessingTime);
        if (isAfter) {
            return true;
        }

        return false;
    }

}
