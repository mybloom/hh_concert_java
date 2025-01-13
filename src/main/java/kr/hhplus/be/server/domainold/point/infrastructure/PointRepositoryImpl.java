package kr.hhplus.be.server.domainold.point.infrastructure;

import java.util.Optional;
import kr.hhplus.be.server.domainold.point.domain.Point;
import kr.hhplus.be.server.domainold.point.domain.PointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PointRepositoryImpl implements PointRepository {

    private final PointJpaRepository pointJpaRepository;

    @Override
    public Optional<Point> findByUserId(long userId) {
        return pointJpaRepository.findByUserId(userId);
    }

    @Override
    public Point save(Point point) {
        return pointJpaRepository.save(point);
    }
}
