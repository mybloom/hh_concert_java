package kr.hhplus.be.server.domainold.point.infrastructure;

import java.util.Optional;
import kr.hhplus.be.server.domainold.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointJpaRepository extends JpaRepository<Point, Long> {

    Optional<Point> findByUserId(Long userId);
}
