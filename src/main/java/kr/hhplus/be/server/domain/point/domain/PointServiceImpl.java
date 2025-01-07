package kr.hhplus.be.server.domain.point.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PointServiceImpl implements PointService {

    private final PointRepository pointRepository;

    @Override
    public long charge(long userId, long amount) {
        Long balance = pointRepository.findByUserId(userId)
            .map(point -> point.charge(amount))
            .orElseThrow();

        return balance;
    }

    @Override
    public long use(long userId, long amount) {
        Point point = pointRepository.findByUserId(userId)
            .orElseThrow();
        point.use(amount);

        pointRepository.save(point);
        return point.getBalance();
    }

    @Override
    public long getBalance(long userId) {
        Point point = pointRepository.findByUserId(userId)
            .orElseThrow();

        return point.getBalance();
    }
}
