package kr.hhplus.be.server.domain.queuetoken.application;

import kr.hhplus.be.server.domain.queuetoken.application.result.QueueTokenResult;
import kr.hhplus.be.server.domain.queuetoken.application.result.WaitQueueTokenResult;
import kr.hhplus.be.server.domain.queuetoken.model.QueueOffset;
import kr.hhplus.be.server.domain.queuetoken.model.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenProperties;
import kr.hhplus.be.server.domain.queuetoken.model.TokenUuidGenerator;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueOffsetRepository;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class QueueTokenCreateService {


    private static final long FIRST_OFFSET_ID = 1L;

    private final TokenUuidGenerator tokenUuidGenerator;
    private final QueueTokenProperties queueTokenProperties;
    private final QueueTokenRepository queueTokenRepository;
    private final QueueOffsetRepository queueOffsetRepository;

    private final QueueOffsetService queueOffsetService;


    public QueueTokenResult createActiveQueueToken(final long userId) {
        final String tokenUuid = tokenUuidGenerator.generateTokenUuid();
        QueueToken queueToken = QueueToken.createActiveToken(
            userId,
            tokenUuid,
            queueTokenProperties.getInitExpirationMinutes()
        );

        queueToken = queueTokenRepository.save(queueToken);

        //lastActiveOffset 증가 처리
        QueueOffset queueOffset = queueOffsetService.retrieveQueueOffset();
        queueOffset.increaseOffsetByOne();
        queueOffsetRepository.save(queueOffset);

        return QueueTokenResult.activeQueueTokenResponse(queueToken.getTokenUuid(), queueToken.getStatus());
    }

    public QueueTokenResult createWaitQueueToken(final long userId) {
        final String tokenUuid = tokenUuidGenerator.generateTokenUuid();
        QueueToken queueToken = QueueToken.createWaitToken(
            userId,
            tokenUuid
        );

        queueToken = queueTokenRepository.save(queueToken);

        //waitOffset 계산
        WaitQueueTokenResult waitQueueTokenResult = WaitOffsetCalculator.calculate(
            queueToken,
            queueOffsetService.retrieveQueueOffset()
        );

        return QueueTokenResult.waitQueueTokenResponse(queueToken.getTokenUuid(), queueToken.getStatus(), waitQueueTokenResult);
    }

}
