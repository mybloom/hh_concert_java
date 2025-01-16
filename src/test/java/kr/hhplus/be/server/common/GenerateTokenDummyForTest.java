package kr.hhplus.be.server.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import kr.hhplus.be.server.domain.queuetoken.model.QueueOffset;
import kr.hhplus.be.server.domain.queuetoken.model.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenProperties;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueOffsetRepository;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueTokenRepository;
import kr.hhplus.be.server.domain.user.domain.User;
import kr.hhplus.be.server.domain.user.repository.UserRepository;


public class GenerateTokenDummyForTest {

    private final QueueTokenRepository queueTokenRepository;
    private final UserRepository userRepository;
    private final QueueTokenProperties queueTokenProperties;
    private final QueueOffsetRepository queueOffsetRepository;

    public GenerateTokenDummyForTest(
        QueueTokenRepository queueTokenRepository,
        UserRepository userRepository,
        QueueTokenProperties queueTokenProperties,
        QueueOffsetRepository queueOffsetRepository) {
        this.queueTokenRepository = queueTokenRepository;
        this.userRepository = userRepository;
        this.queueTokenProperties = queueTokenProperties;
        this.queueOffsetRepository = queueOffsetRepository;
    }

    public void generateTokens(int activeCount, int invalidCount, int waitSize) {
        long lastActiveOffset = activeCount + invalidCount;
        queueOffsetRepository.save(QueueOffset.of(lastActiveOffset));
        generateActiveOrInvalidQueueTokens(activeCount, invalidCount);
        generateWaitTokens(waitSize);
    }

    public void generateActiveOrInvalidQueueTokens(int activeCount, int invalidCount) {
        List<QueueToken> tokens = new ArrayList<>();

        for (int i = 0; i < activeCount + invalidCount; i++) {
            User user = userRepository.save(new User());

            String tokenUuid = UUID.randomUUID().toString();
            QueueToken queueToken = QueueToken.createActiveToken(
                user.getId(), tokenUuid, queueTokenProperties.getInitExpirationMinutes()
            );

            // INVALID 상태로 설정할 조건
            if (i >= activeCount) {
                queueToken.invalidate();
            }

            tokens.add(queueToken);
        }

        Collections.shuffle(tokens);
        queueTokenRepository.saveAll(tokens);
    }


    public void generateWaitTokens(int count) {
        List<QueueToken> tokens = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            User user = userRepository.save(new User());

            String tokenUuid = UUID.randomUUID().toString();
            QueueToken queueToken = QueueToken.createWaitToken(user.getId(), tokenUuid);
            tokens.add(queueToken);
        }

        queueTokenRepository.saveAll(tokens);
    }

}
