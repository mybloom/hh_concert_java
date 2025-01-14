package kr.hhplus.be.server.domain.point.repository;

import java.util.Optional;
import kr.hhplus.be.server.domain.point.model.Point;

public interface PointQueryRepository {
    Optional<Point> findByUserIdWithLock(long userId);

}
