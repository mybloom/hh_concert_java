package kr.hhplus.be.server.domain.queuetoken.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.hhplus.be.server.domain.queuetoken.domain.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.domain.QueueTokenStatus;
import kr.hhplus.be.server.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QueueTokenJpaRepository extends JpaRepository<QueueToken, Long> {

    long countByStatus(QueueTokenStatus status);

    long countByStatusIn(List<QueueTokenStatus> statusList);

    Optional<QueueToken> findByUser(User user);

    @Query("SELECT q FROM QueueToken q WHERE q.id = (SELECT MAX(q2.id) FROM QueueToken q2)")
    Optional<QueueToken> findQueueTokenWithMaxId();

    Optional<QueueToken> findByTokenUuid(String tokenUuid);

    List<QueueToken> findAllByStatusIn(List<QueueTokenStatus> statusList);
    List<QueueToken> findAllByStatus(QueueTokenStatus status);

    Optional<QueueToken> findTopByStatusOrderByIdDesc(QueueTokenStatus status);

    @Modifying
    @Query("UPDATE QueueToken q SET q.status = :status, q.waitOffset = :waitOffset, q.runningExpiredAt = :runningExpiredAt WHERE q.id = :id")
    int updateTokenStatusAndRunningExpiredAt(@Param("id") long id,
        @Param("status") QueueTokenStatus status,
        @Param("waitOffset") long waitOffset,
        @Param("runningExpiredAt") LocalDateTime runningExpiredAt);

    @Modifying
    @Query("UPDATE QueueToken q SET q.status = :status, q.waitOffset = :waitOffset, q.runningExpiredAt = :runningExpiredAt WHERE q.id = :id")
    int updateTokenStatusAndOffset(@Param("id") long id,
        @Param("status") QueueTokenStatus status,
        @Param("waitOffset") long waitOffset
        );

    List<QueueToken> findByIdGreaterThanEqual(Long id);

}
