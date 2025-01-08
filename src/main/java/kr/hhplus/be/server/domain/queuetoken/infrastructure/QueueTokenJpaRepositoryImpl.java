package kr.hhplus.be.server.domain.queuetoken.infrastructure;

import kr.hhplus.be.server.domain.queuetoken.domain.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.domain.QueueTokenRepository;
import kr.hhplus.be.server.domain.queuetoken.domain.QueueTokenStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class QueueTokenJpaRepositoryImpl implements QueueTokenRepository {

    private final QueueTokenJpaRepository queueTokenJpaRepository;

    @Override
    public long countByStatus(QueueTokenStatus status) {
        return queueTokenJpaRepository.countByStatus(status);
    }

    @Override
    public QueueToken save(QueueToken token) {
        return queueTokenJpaRepository.save(token);
    }
}
