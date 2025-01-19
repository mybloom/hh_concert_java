package kr.hhplus.be.server.domain.queuetoken.application;

import static kr.hhplus.be.server.common.exception.errorcode.IllegalArgumentErrorCode.QUEUE_TOKEN_NOT_FOUND;

import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenProperties;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QueueTokenValidator {

    private final QueueTokenProperties queueTokenProperties;
    private final QueueTokenRepository queueTokenRepository;


    public boolean isValidToken(String tokenUuid) {
        kr.hhplus.be.server.domain.queuetoken.model.QueueToken queueToken = queueTokenRepository.findByTokenUuid(tokenUuid)
            .orElseThrow(() -> new BusinessIllegalArgumentException(QUEUE_TOKEN_NOT_FOUND));

        int initExpirationMinutes = queueTokenProperties.getInitExpirationMinutes();

        boolean expired = queueToken.isExpired(initExpirationMinutes);
        return !expired;
    }
}

