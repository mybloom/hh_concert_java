package kr.hhplus.be.server.domain.queuetoken.application;

import java.time.LocalDateTime;
import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenProperties;
import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenStatus;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class QueueTokenModifyService {

    private final QueueTokenProperties queueTokenProperties;
    private final QueueTokenRepository queueTokenRepository;


    public long activateToken(long beforeLastActiveOffset, long invalidCount) {
        // WAIT 상태의 토큰 개수 확인
        long availableTokenCount = queueTokenRepository.countByIdGreaterThanAndStatus(
            beforeLastActiveOffset,
            QueueTokenStatus.WAIT
        );

        // 실제 처리 가능한 토큰 수 결정
        long processCount = Math.min(availableTokenCount, invalidCount);

        //WAIT 상태의 토큰을 ACTIVE 상태로 변경.
        //(beforeLastActiveOffset +1 ~ lastActiveOffset) 까지의 WAIT 상태 토큰을 ACTIVE 로 변경. waitOffset = 0 처리
        long startTokenId = beforeLastActiveOffset + 1;
        int initExpirationMinutes = queueTokenProperties.getInitExpirationMinutes();
        for (long i = 0; i < processCount; i++) {
            queueTokenRepository.updateTokenStatusAndOffset(
                startTokenId + i, QueueTokenStatus.ACTIVE, 0L, LocalDateTime.now().plusMinutes(initExpirationMinutes)
            );
        }

        long lastProcessedTokenId = startTokenId + processCount - 1;
        return lastProcessedTokenId;
    }

}
