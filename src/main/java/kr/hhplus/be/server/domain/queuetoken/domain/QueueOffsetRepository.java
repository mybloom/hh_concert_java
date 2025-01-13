package kr.hhplus.be.server.domain.queuetoken.domain;

import java.util.Optional;
import org.springframework.data.repository.query.Param;

public interface QueueOffsetRepository {

    Optional<QueueOffset> findByIdWithLock(long id);

    QueueOffset save(QueueOffset queueOffset);

    int updateLastActiveOffset(@Param("id") long id, @Param("lastActiveOffset") long lastActiveOffset);
}
