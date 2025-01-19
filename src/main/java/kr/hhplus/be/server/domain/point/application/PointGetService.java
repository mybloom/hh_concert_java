package kr.hhplus.be.server.domain.point.application;

import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.point.repository.PointQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointGetService {

    private final PointQueryRepository pointQueryRepository;

    public long getBalance(long userId) {
        Point point = pointQueryRepository.findByUserId(userId)
            .orElseGet(() -> Point.createInitBalance(userId));

        return point.getBalance();
    }

}
