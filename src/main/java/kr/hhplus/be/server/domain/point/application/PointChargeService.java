package kr.hhplus.be.server.domain.point.application;

import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.point.repository.PointCommandRepository;
import kr.hhplus.be.server.domain.point.repository.PointQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointChargeService {

    private final PointQueryRepository pointQueryRepository;
    private final PointCommandRepository pointCommandRepository;

    /**
     * 요구사항
     * 1. 사용자의 포인트를 조회한 후 충전한다.
     * 2. 가입된 사용자라도 포인트 충전을 한 번도 안한 사용자는 포인트 테이블에 데이터가 없다.
     * 유의사항
     * 1. 포인트 충전은 동시성 문제를 해결해야 한다. : JPA의 비관적락 사용.
     */
    @Transactional
    public long charge(long userId, long amount) {
        Point point = pointQueryRepository.findByUserIdWithLock(userId)
            .orElseGet(() -> Point.createInitBalance(userId));
        point.charge(amount);

        point = pointCommandRepository.save(point); //이미 존재하는 경우 자동 update

        return point.getBalance();
    }

}
