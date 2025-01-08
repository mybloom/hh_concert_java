package kr.hhplus.be.server.domain.queuetoken.domain;


import java.util.Optional;
import kr.hhplus.be.server.domain.user.domain.User;

public interface QueueTokenRepository {

    long countByStatus(QueueTokenStatus status);

    QueueToken save(QueueToken token);

    Optional<QueueToken> findByUser(User user);

    void delete(QueueToken queueToken);
}
