package kr.hhplus.be.server.domain.point.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.point.repository.PointCommandRepository;
import kr.hhplus.be.server.domain.point.repository.PointQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * 테스트코드
 * - 충전이력 여부에 따라 로직이 달라지는 부분을 작성
 * - 메서드 호출 순서와 오케스트레이션 검증
 */
@ExtendWith(MockitoExtension.class)
class PointChargeServiceUnitTest {
    @InjectMocks
    private PointChargeService pointChargeService;

    @Mock
    private PointCommandRepository pointCommandRepository;

    @Mock
    private PointQueryRepository pointQueryRepository;

    @DisplayName("충전한 이력이 있는 사용자가 100원을 충전한다.")
    @Test
    void unitTestChargeForExistingUser() {
        // given: 기존 포인트가 있는 경우
        long userId = 1L;
        long chargeAmount = 100L;
        Point point = spy(Point.createInitBalance(userId)); //Spy 객체 사용해서 mockito가 Point.charge()를 감지할 수 있도록 함

        when(pointQueryRepository.findByUserIdWithLock(userId))
            .thenReturn(Optional.of(point));
        when(pointCommandRepository.save(point)).thenReturn(point);

        // when: 포인트 충전 수행
        long resultBalance = pointChargeService.charge(userId, chargeAmount);

        // then: 메서드 호출 순서와 오케스트레이션 검증
        verify(pointQueryRepository).findByUserIdWithLock(userId);  // 1. 조회
        verify(point).charge(chargeAmount);                 // 2. 충전
        verify(pointCommandRepository).save(point);         // 3. 저장  : 메서드가 한 번 호출되었는지 검증
        assertThat(resultBalance).isEqualTo(chargeAmount);   // 4. 잔액 검증
    }

    @DisplayName("충전한 이력이 없는 사용자가 100원을 충전한다.")
    @Test
    void unitTestChargeForNoExistingUser() {
        // Given: 포인트가 없는 경우
        long userId = 2L;
        long chargeAmount = 100L;
        Point point = spy(Point.createInitBalance(userId));
        point.charge(chargeAmount);

        given(pointQueryRepository.findByUserIdWithLock(userId))
            .willReturn(Optional.empty());
        given(pointCommandRepository.save(any(Point.class)))
            .willReturn(point);

        // When: 포인트 충전 수행
        long resultBalance = pointChargeService.charge(userId, chargeAmount);

        // Then: 오케스트레이션 검증
        verify(pointQueryRepository).findByUserIdWithLock(userId);  // 1. 조회
        verify(point).charge(chargeAmount);                      // 2. 초기화 후 충전
        verify(pointCommandRepository).save(any(Point.class));      // 3. 새로 저장
        assertThat(resultBalance).isEqualTo(chargeAmount);                  // 4. 잔액 검증
    }

}