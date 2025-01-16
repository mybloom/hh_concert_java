package kr.hhplus.be.server.domain.point.infrastructure;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.hhplus.be.server.domain.point.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointJpaRepository extends JpaRepository<Point, Long> {

    Optional<Point> findByUserId(long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Point p where p.userId = :userId")
    Optional<Point> findByUserIdWithLock(@Param("userId") Long userId);
}
