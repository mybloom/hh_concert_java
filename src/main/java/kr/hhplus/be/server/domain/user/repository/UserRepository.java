package kr.hhplus.be.server.domain.user.repository;

import java.util.Optional;
import kr.hhplus.be.server.domain.user.domain.User;

public interface UserRepository {
    Optional<User> findById(Long userId);

    User save(User user);
}
