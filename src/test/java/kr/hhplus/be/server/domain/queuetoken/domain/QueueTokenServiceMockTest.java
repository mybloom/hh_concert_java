package kr.hhplus.be.server.domain.queuetoken.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import kr.hhplus.be.server.domain.queuetoken.domain.dto.QueueTokenResponse;
import kr.hhplus.be.server.domain.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QueueTokenServiceMockTest {

    @InjectMocks
    private QueueTokenService queueTokenService;

    @Mock
    private QueueTokenRepository queueTokenRepository;

    @Mock
    private QueueTokenProperties queueTokenProperties;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "User1");
    }

    @DisplayName("서버의 처리 임계치를 넘지 않았을 때 ACTIVE 상태의 토큰이 생성됨을 확인한다")
    @Test
    void testCreateActiveToken() {
        // Given
        when(queueTokenRepository.findByUser(user)).thenReturn(Optional.empty());

        when(queueTokenRepository.countByStatusIn(List.of(QueueTokenStatus.ACTIVE, QueueTokenStatus.INVALID))).thenReturn(0L);
        when(queueTokenProperties.getThreshold()).thenReturn(10);

        QueueToken activeQueueToken = QueueToken.createActiveToken(user);
        when(queueTokenRepository.save(any())).thenReturn(activeQueueToken);

        // When
        QueueTokenResponse response = queueTokenService.create(user);

        // Then
        verify(queueTokenRepository).save(any());
        //extracting: 객체의 특정 프로퍼티를 추출하고 해당 값을 검증
        assertThat(response).extracting(QueueTokenResponse::getTokenUuid).isNotNull();
        assertThat(response).extracting(QueueTokenResponse::getStatus).isEqualTo(QueueTokenStatus.ACTIVE);
    }

    @DisplayName("서버 임계치 초과 시 WAIT 상태 토큰 생성과 waitOffset 증가를 확인한다")
    @Test
    void testCreateWaitToken() {
        // Given
        when(queueTokenRepository.findByUser(user)).thenReturn(Optional.empty());

        when(queueTokenRepository.countByStatusIn(List.of(QueueTokenStatus.ACTIVE, QueueTokenStatus.INVALID))).thenReturn(10L);
        when(queueTokenProperties.getThreshold()).thenReturn(10);

        QueueToken queueTokenWithMaxId = QueueToken.createWaitToken(user, 1L);
        when(queueTokenRepository.findQueueTokenWithMaxId()).thenReturn(Optional.of(queueTokenWithMaxId));

        QueueToken queueToken = QueueToken.createWaitToken(user, 2L);
        when(queueTokenRepository.save(any())).thenReturn(queueToken);

        // When
        QueueTokenResponse response = queueTokenService.create(user);

        // Then
        verify(queueTokenRepository).save(any());
        assertThat(response.getTokenUuid()).isNotNull();
        assertThat(response.getStatus()).isEqualTo(QueueTokenStatus.WAIT);
        assertThat(response.getWaitTokenInfo().getWaitOffset()).isEqualTo(2);
    }

    @DisplayName("기존 토큰이 있으면 제거되고 새로 생성되는지 확인한다")
    @Test
    void removeAndCreateNewToken() {
        // Given
        QueueToken existingToken = QueueToken.createActiveToken(user);
        when(queueTokenRepository.findByUser(user)).thenReturn(Optional.of(existingToken));

        when(queueTokenRepository.countByStatusIn(List.of(QueueTokenStatus.ACTIVE, QueueTokenStatus.INVALID))).thenReturn(0L);
        when(queueTokenProperties.getThreshold()).thenReturn(10);

        QueueToken newToken = QueueToken.createActiveToken(user);
        when(queueTokenRepository.save(any())).thenReturn(newToken);

        // When
        QueueTokenResponse response = queueTokenService.create(user);

        // Then
        verify(queueTokenRepository).delete(existingToken);
        verify(queueTokenRepository).save(any());
        assertThat(response).extracting(QueueTokenResponse::getTokenUuid).isNotNull();
    }

}