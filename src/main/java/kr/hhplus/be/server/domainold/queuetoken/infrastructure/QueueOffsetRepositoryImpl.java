package kr.hhplus.be.server.domainold.queuetoken.infrastructure;

import java.util.Optional;
import kr.hhplus.be.server.domainold.queuetoken.domain.QueueOffset;
import kr.hhplus.be.server.domainold.queuetoken.domain.QueueOffsetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class QueueOffsetRepositoryImpl implements QueueOffsetRepository {

    private final QueueOffsetJpaRepository queueOffsetJpaRepository;

    @Override
    public Optional<QueueOffset> findByIdWithLock(long id) {
        return queueOffsetJpaRepository.findByIdWithLock(id);
    }

    @Override
    public QueueOffset save(QueueOffset queueOffset) {
        return queueOffsetJpaRepository.save(queueOffset);
    }

    @Override
    public int updateLastActiveOffset(long id, long lastActiveOffset) {
        return queueOffsetJpaRepository.updateLastActiveOffset(id, lastActiveOffset);
    }
}
