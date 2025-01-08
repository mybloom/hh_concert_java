package kr.hhplus.be.server.domain.queuetoken.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

    private QueueTokenStatus status;

    private LocalDateTime runningExpiredAt;

    private Long waitOrder;

    public static boolean isExceededLimit(long threshold, long runningTokenCounts) {
        if (runningTokenCounts >= threshold) {
            return true;
        }
        return false;
    }

    public static QueueToken createWaitToken(User user, Long waitOrder) {
        return QueueToken.builder()
            .user(user)
            .status(QueueTokenStatus.WAIT)
            .tokenUuid(generateUuid())
            .waitOrder(waitOrder)
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
        return LocalDateTime.now().plusMinutes(5);
    }
}
