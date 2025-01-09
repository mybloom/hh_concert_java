package kr.hhplus.be.server.domain.queuetoken.infrastructure;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import kr.hhplus.be.server.domain.queuetoken.domain.QueueOffset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QueueOffsetJpaRepository extends JpaRepository<QueueOffset, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT q FROM QueueOffset q WHERE q.id = :id")
    Optional<QueueOffset> findByIdWithLock(@Param("id") long id);

    @Modifying
    @Query("UPDATE QueueOffset q SET q.lastActiveOffset = :lastActiveOffset " +
        "WHERE q.id = :id")
    int updateLastActiveOffset(@Param("id") long id, @Param("lastActiveOffset") long lastActiveOffset);
}
