package kr.hhplus.be.server.domain.queuetoken.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class QueueOffset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //todo: 여러 인스턴스 동시성 이슈 처리 및 테스트
    //todo: 스케줄러에서 처리할 수 있도록 변경
    private Long lastActiveOffset;

    public void increaseOffsetByOne() {
        this.lastActiveOffset++;
    }

    public void increaseOffset(long offset) {
        this.lastActiveOffset = offset;
    }

}
