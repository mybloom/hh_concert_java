package kr.hhplus.be.server.domainold.user.infrastructure;

import java.util.Optional;
import kr.hhplus.be.server.domainold.user.domain.User;
import kr.hhplus.be.server.domainold.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public Optional<User> findById(Long userId) {
        return userJpaRepository.findById(userId);
    }
}
