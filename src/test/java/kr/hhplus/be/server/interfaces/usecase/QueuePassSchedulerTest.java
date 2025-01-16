package kr.hhplus.be.server.interfaces.usecase;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import kr.hhplus.be.server.common.ControllerIntegrationTest;
import kr.hhplus.be.server.common.GenerateTokenDummyForTest;
import kr.hhplus.be.server.domain.queuetoken.model.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.model.QueueTokenStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * 테스트코드 - 실제로 대기열 DB 테이블에 대한 처리가 잘되었는지 확인하는 통합테스트
 */
@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QueuePassSchedulerTest extends ControllerIntegrationTest {

    @Autowired
    QueuePassScheduler queuePassScheduler;

    @DisplayName("처리 임계치 넘었을 때 대기열 통과 처리 성공 테스트")
    @Test
    void testAfterThresholdQueuePass() {
        //given
        GenerateTokenDummyForTest generatedDummy = new GenerateTokenDummyForTest(
            queueTokenRepository,
            userRepository,
            queueTokenProperties,
            queueOffsetRepository
        );
        int activeSize = 30;
        int invalidSize = 20;
        int waitSize = 15;
        generatedDummy.generateTokens(activeSize, invalidSize, waitSize);

        //when
        queuePassScheduler.processAfterThreshold();

        //then
        // 1. 총 ACTIVE 토큰 개수 확인
        long activeTokenCount = queueTokenRepository.countByStatus(QueueTokenStatus.ACTIVE);
        assertThat(activeTokenCount).isEqualTo(activeSize + waitSize);

        // 2. 처리 후 남은 WAIT 상태 토큰 개수 확인
        long remainingWaitCount = queueTokenRepository.countByStatus(QueueTokenStatus.WAIT);
        assertThat(remainingWaitCount).isEqualTo(Math.max((waitSize - invalidSize), 0));

        // 3. 처리된 토큰의 속성 확인 (예: offset, expirationTime 등)
        List<QueueToken> processedTokens = queueTokenRepository.findAllByStatus(QueueTokenStatus.ACTIVE);
        assertThat(processedTokens.stream()
            .map(QueueToken::getUserId)
            .collect(Collectors.toSet())
            .size())
            .isEqualTo(processedTokens.size());
    }

    @DisplayName("처리 임계치 넘지 않았을 때 만료 토큰 제거 성공 테스트")
    @Test
    void testBeforeThresholdQueuePass() {
        //given
        GenerateTokenDummyForTest generatedDummy = new GenerateTokenDummyForTest(
            queueTokenRepository,
            userRepository,
            queueTokenProperties,
            queueOffsetRepository
        );
        int activeSize = 30;
        int invalidSize = 20;
        int waitSize = 0;
        generatedDummy.generateTokens(activeSize, invalidSize, waitSize);

        //when
        queuePassScheduler.processBeforeThreshold();

        //then
        // 1. 총 ACTIVE 토큰 개수 확인
        long activeTokenCount = queueTokenRepository.countByStatus(QueueTokenStatus.ACTIVE);
        assertThat(activeTokenCount).isEqualTo(activeSize);

        // 2. 처리 후 남은 INVALID 상태 토큰 개수 확인
        long remainingInvalidCount = queueTokenRepository.countByStatus(QueueTokenStatus.INVALID);
        assertThat(remainingInvalidCount).isEqualTo(0L);
    }

}