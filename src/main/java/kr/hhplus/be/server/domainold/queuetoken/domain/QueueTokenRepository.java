package kr.hhplus.be.server.domainold.queuetoken.domain;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.hhplus.be.server.domainold.user.domain.User;

public interface QueueTokenRepository {

    long countByStatus(QueueTokenStatus status);

    long countByStatusIn(List<QueueTokenStatus> statusList);

    QueueToken save(QueueToken token);

    Optional<QueueToken> findByUser(User user);

    void delete(QueueToken queueToken);

    Optional<QueueToken> findQueueTokenWithMaxId();

    Optional<QueueToken> findByTokenUuid(String tokenUuid);

    List<QueueToken> findAllByStatusIn(List<QueueTokenStatus> statusList);

    List<QueueToken> findAllByStatus(QueueTokenStatus status);

    void deleteAllById(Iterable<Long> ids);

    Optional<QueueToken> findTopByStatusOrderByIdDesc(QueueTokenStatus status);

    int updateTokenStatusAndOffset(long id, QueueTokenStatus status, long waitOffset, LocalDateTime runningExpiredAt);

    List<QueueToken> findByIdGreaterThanEqual(Long id);


    int updateTokenStatusAndOffset(long id, QueueTokenStatus wait, long waitOffset);
}
