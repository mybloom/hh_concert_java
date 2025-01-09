package kr.hhplus.be.server.domain.queuetoken.domain;


import java.util.List;
import java.util.Optional;
import kr.hhplus.be.server.domain.user.domain.User;
import org.springframework.data.repository.query.Param;

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

    int updateTokenStatusAndOffset(long id, QueueTokenStatus status, long waitOffset);

    List<QueueToken> findByIdGreaterThanEqual(Long id);


}
