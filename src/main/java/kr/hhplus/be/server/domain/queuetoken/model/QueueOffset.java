package kr.hhplus.be.server.domain.queuetoken.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class QueueOffset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long lastActiveOffset;

    public static QueueOffset of(long lastActiveOffset) {
        return QueueOffset.builder()
            .lastActiveOffset(lastActiveOffset)
            .build();
    }

    public void increaseOffsetByOne() {
        this.lastActiveOffset++;
    }

    public void setLastActiveOffset(long offset) {
        this.lastActiveOffset = offset;
    }

}
