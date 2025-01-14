package kr.hhplus.be.server.domain.point.application;

import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.point.repository.PointCommandRepository;
import kr.hhplus.be.server.domain.point.repository.PointQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointGetService {

    private final PointQueryRepository pointQueryRepository;
    private final PointCommandRepository pointCommandRepository;

    public long getBalance(long userId) {
        Point point = pointQueryRepository.findByUserIdWithLock(userId)
            .orElse(Point.createInitBalance(userId));

        return point.getBalance();
    }

    public long use(long userId, long amount) {
        Point point = pointQueryRepository.findByUserIdWithLock(userId)
            .orElseThrow();
        point.use(amount);

        pointCommandRepository.save(point);
        return point.getBalance();
    }

}
