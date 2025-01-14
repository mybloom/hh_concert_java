package kr.hhplus.be.server.domain.point.infrastructure;

import java.util.Optional;
import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.point.repository.PointCommandRepository;
import kr.hhplus.be.server.domain.point.repository.PointQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PointRepositoryImpl implements PointQueryRepository, PointCommandRepository {

    private final PointJpaRepository pointJpaRepository;

    @Override
    public Optional<Point> findByUserIdWithLock(long userId) {
        return pointJpaRepository.findByUserIdWithLock(userId);
    }

    @Override
    public Point save(Point point) {
        return pointJpaRepository.save(point);
    }
}
