package kr.hhplus.be.server.domainold.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PointServiceImplTest {

    @InjectMocks
    private PointServiceImpl pointService;

    @Mock
    private PointRepository pointRepository;

    @DisplayName("처음 포인트를 충전하는 사용자는 Point 데이터가 없다가 생성된다")
    @Test
    void firstTimePointChargeCreatesData() {
        // given
        long userId = 1L;
        long amount = 100L;

        when(pointRepository.findByUserId(userId)).thenReturn(Optional.empty());

        // Capturing the Point object created for saving
        ArgumentCaptor<Point> pointCaptor = ArgumentCaptor.forClass(Point.class);

        // Mocking the save method to return the saved Point
        when(pointRepository.save(any(Point.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        long balance = pointService.charge(userId, amount);

        // then
        verify(pointRepository).findByUserId(userId);
        verify(pointRepository).save(pointCaptor.capture());

        Point savedPoint = pointCaptor.getValue();
        assertThat(balance).isEqualTo(amount);
        assertThat(userId).isEqualTo(savedPoint.getUserId());
        assertThat(amount).isEqualTo(savedPoint.getBalance());
    }

    @DisplayName("두번째로 포인트를 충전하는 사용자는 기존 Point 데이터에 balance가 추가된다")
    @Test
    void existingUserPointChargeUpdatesBalance() {
        // given
        long userId = 1L;
        long initialBalance = 1000L;
        long additionalAmount = 500L;

        // Mocking an existing Point object
        Point existingPoint = Point.create(userId);
        existingPoint.charge(initialBalance);

        // Mocking PointRepository to return the existing Point
        when(pointRepository.findByUserId(userId)).thenReturn(Optional.of(existingPoint)); //0원

        // Mocking the save method to return the updated Point
        /*.thenAnswer()는 호출된 메서드의 인자를 직접 다룰 수 있게 해줌.
        invocation.getArgument(0)는 호출된 메서드의 첫 번째 인자를 가져오는데, 여기서는 Point 객체를 가져옴.
         */
        when(pointRepository.save(any(Point.class))).thenAnswer(invocation -> invocation.getArgument(0)); //1000원

        // when
        long updatedBalance = pointService.charge(userId, additionalAmount); //1500원

        // then
        verify(pointRepository).findByUserId(userId);
        verify(pointRepository).save(existingPoint);

        // Assert the balance is updated correctly
        assertEquals(initialBalance + additionalAmount, updatedBalance);
        assertEquals(initialBalance + additionalAmount, existingPoint.getBalance());
    }

}