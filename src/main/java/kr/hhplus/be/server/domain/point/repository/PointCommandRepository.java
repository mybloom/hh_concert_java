package kr.hhplus.be.server.domain.point.repository;

import kr.hhplus.be.server.domain.point.model.Point;

public interface PointCommandRepository {

    Point save(Point point);
}
