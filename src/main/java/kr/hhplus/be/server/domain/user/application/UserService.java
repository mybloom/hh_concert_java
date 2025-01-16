package kr.hhplus.be.server.domain.user.application;

import static kr.hhplus.be.server.common.exception.errorcode.IllegalArgumentErrorCode.USER_NOT_FOUND;

import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.domain.user.domain.User;
import kr.hhplus.be.server.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User retrieveUser(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new BusinessIllegalArgumentException(USER_NOT_FOUND));
    }

}
