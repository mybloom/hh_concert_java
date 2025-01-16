package kr.hhplus.be.server.domain.queuetoken.application;

import kr.hhplus.be.server.domain.queuetoken.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueueTokenClearService {

    private final QueueTokenRepository queueTokenRepository;

    public void clearExistingToken(long userId) {
        queueTokenRepository.findByUserId(userId)
            .ifPresent(queueToken -> queueTokenRepository.delete(queueToken));
    }
}
