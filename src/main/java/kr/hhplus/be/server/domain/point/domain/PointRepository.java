package kr.hhplus.be.server.domain.point.domain;

import java.util.Optional;

public interface PointRepository {
    Optional<Point> findByUserId(long userId);

    void save(Point point);
}
