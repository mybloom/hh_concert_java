package kr.hhplus.be.server.domain.queuetoken.infrastructure;

import java.util.Optional;
import kr.hhplus.be.server.domain.queuetoken.domain.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.domain.QueueTokenRepository;
import kr.hhplus.be.server.domain.queuetoken.domain.QueueTokenStatus;
import kr.hhplus.be.server.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class QueueTokenRepositoryImpl implements QueueTokenRepository {

    private final QueueTokenJpaRepository queueTokenJpaRepository;

    @Override
    public long countByStatus(QueueTokenStatus status) {
        return queueTokenJpaRepository.countByStatus(status);
    }

    @Override
    public QueueToken save(QueueToken token) {
        return queueTokenJpaRepository.save(token);
    }

    @Override
    public Optional<QueueToken> findByUser(User user) {
        return queueTokenJpaRepository.findByUser(user);
    }

    @Override
    public void delete(QueueToken queueToken) {
        queueTokenJpaRepository.delete(queueToken);
    }

    @Override
    public QueueToken findQueueTokenWithMaxId() {
        return queueTokenJpaRepository.findQueueTokenWithMaxId();
    }

    @Override
    public Optional<QueueToken> findByTokenUuid(String tokenUuid) {
        return queueTokenJpaRepository.findByTokenUuid(tokenUuid);
    }
}
