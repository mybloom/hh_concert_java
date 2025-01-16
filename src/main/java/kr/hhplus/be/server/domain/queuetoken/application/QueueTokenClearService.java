package kr.hhplus.be.server.domain.queuetoken.application;

import java.util.concurrent.atomic.AtomicLong;
import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenProperties;
import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenStatus;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueueTokenClearService {

    private final QueueTokenProperties queueTokenProperties;
    private final QueueTokenRepository queueTokenRepository;

    public void clearExistingToken(long userId) {
        queueTokenRepository.findByUserId(userId)
            .ifPresent(queueToken -> queueTokenRepository.delete(queueToken));
    }

    public long clearAllInvalidTokens() {
        AtomicLong expirationTokenCount = new AtomicLong();

        int paymentExpirationMinutes = queueTokenProperties.getPaymentExpirationMinutes();
        queueTokenRepository.findAllByStatus(QueueTokenStatus.INVALID)
            .forEach(queueToken -> {
                queueTokenRepository.delete(queueToken);
                expirationTokenCount.getAndIncrement();
            });
        queueTokenRepository.findAllByStatus(QueueTokenStatus.ACTIVE)
            .forEach(queueToken -> {
                if (queueToken.isExpirationTimeReached(paymentExpirationMinutes)) {
                    queueTokenRepository.delete(queueToken);
                    expirationTokenCount.getAndIncrement();
                }
            });

        long clearTokenCount = expirationTokenCount.get();
        return clearTokenCount;
    }

}
