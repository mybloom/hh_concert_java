package kr.hhplus.be.server.domain.queuetoken.application;

import static kr.hhplus.be.server.common.exception.errorcode.IllegalArgumentErrorCode.QUEUE_OFFSET_NOT_FOUND;

import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
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


    public QueueTokenResult createActiveQueueToken(final long userId) {
        final String tokenUuid = tokenUuidGenerator.generateTokenUuid();
        QueueToken queueToken = QueueToken.createActiveToken(
            userId,
            tokenUuid,
            queueTokenProperties.getInitExpirationMinutes()
        );

        queueToken = queueTokenRepository.save(queueToken);

        //lastActiveOffset 증가 처리
        QueueOffset queueOffset = retrieveQueueOffset();
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
        WaitQueueTokenResult waitQueueTokenResult = WaitOffsetCalculator.calculate(queueToken, retrieveQueueOffset());

        return QueueTokenResult.waitQueueTokenResponse(queueToken.getTokenUuid(), queueToken.getStatus(), waitQueueTokenResult);
    }

    private QueueOffset retrieveQueueOffset() {
        QueueOffset queueOffset = queueOffsetRepository.findById(FIRST_OFFSET_ID)
            .orElseThrow(() -> new BusinessIllegalArgumentException(QUEUE_OFFSET_NOT_FOUND));
        return queueOffset;
    }
}
