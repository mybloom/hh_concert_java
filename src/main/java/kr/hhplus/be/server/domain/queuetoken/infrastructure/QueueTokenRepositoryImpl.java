package kr.hhplus.be.server.domain.queuetoken.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.hhplus.be.server.domain.queuetoken.model.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenStatus;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueTokenRepository;
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
    public long countByStatusIn(List<QueueTokenStatus> statusList) {
        return queueTokenJpaRepository.countByStatusIn(statusList);
    }

    @Override
    public QueueToken save(QueueToken token) {
        return queueTokenJpaRepository.save(token);
    }

    @Override
    public Optional<QueueToken> findByUserId(long userId) {
        return queueTokenJpaRepository.findByUserId(userId);
    }

    @Override
    public void delete(QueueToken queueToken) {
        queueTokenJpaRepository.delete(queueToken);
    }

    @Override
    public Optional<QueueToken> findQueueTokenWithMaxId() {
        return queueTokenJpaRepository.findQueueTokenWithMaxId();
    }

    @Override
    public Optional<QueueToken> findByTokenUuid(String tokenUuid) {
        return queueTokenJpaRepository.findByTokenUuid(tokenUuid);
    }

    @Override
    public List<QueueToken> findAllByStatusIn(List<QueueTokenStatus> statusList) {
        return queueTokenJpaRepository.findAllByStatusIn(statusList);
    }

    @Override
    public List<QueueToken> findAllByStatus(QueueTokenStatus status) {
        return queueTokenJpaRepository.findAllByStatus(status);
    }

    @Override
    public void deleteAllById(Iterable<Long> ids) {
        queueTokenJpaRepository.deleteAllById(ids);
    }

    @Override
    public Optional<QueueToken> findTopByStatusOrderByIdDesc(QueueTokenStatus status) {
        return queueTokenJpaRepository.findTopByStatusOrderByIdDesc(status);
    }

    @Override
    public int updateTokenStatusAndOffset(long id, QueueTokenStatus status, long waitOffset, LocalDateTime runningExpiredAt) {
        return queueTokenJpaRepository.updateTokenStatusAndRunningExpiredAt(id, status, waitOffset, runningExpiredAt);
    }

    @Override
    public int updateTokenStatusAndOffset(long id, QueueTokenStatus wait, long waitOffset) {
        return queueTokenJpaRepository.updateTokenStatusAndOffset(id, wait, waitOffset);
    }

    @Override
    public List<QueueToken> findByIdGreaterThanEqual(Long id) {
        return queueTokenJpaRepository.findByIdGreaterThanEqual(id);
    }
}
