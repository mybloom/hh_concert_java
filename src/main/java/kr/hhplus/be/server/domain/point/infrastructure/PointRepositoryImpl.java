package kr.hhplus.be.server.domain.point.infrastructure;

import java.util.Optional;
import kr.hhplus.be.server.domain.point.domain.Point;
import kr.hhplus.be.server.domain.point.domain.PointRepository;
import org.springframework.stereotype.Repository;

@Repository
public class PointRepositoryImpl implements PointRepository {

    @Override
    public Optional<Point> findByUserId(long userId) {
        return Optional.empty();
    }
}
