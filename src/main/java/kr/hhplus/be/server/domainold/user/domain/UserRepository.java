package kr.hhplus.be.server.domainold.user.domain;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(Long userId);
}
