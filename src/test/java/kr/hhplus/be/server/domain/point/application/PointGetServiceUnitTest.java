package kr.hhplus.be.server.domain.point.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.point.repository.PointQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) // Mockito 확장을 추가하여 목 객체를 사용할 수 있게 함
class PointGetServiceUnitTest {

    @InjectMocks
    private PointGetService pointGetService;

    @Mock
    private PointQueryRepository pointQueryRepository;


    @DisplayName("충전이력이 없는 사용자 ID로, 포인트 0원을 조회할 수 있다.")
    @Test
    void testGetBalanceWitNoChargeHistory() {
        //given
        final long userId = 1L;
        final long expectedBalance = 0;

        when(pointQueryRepository.findByUserId(userId))
            .thenReturn(Optional.empty());

        //when
        long balance = pointGetService.getBalance(userId);

        //then
        assertThat(balance).isEqualTo(expectedBalance);
    }

    @DisplayName("충전 이력이 있는 사용자 ID로, 보유한 포인트 100원을 조회할 수 있다.")
    @Test
    void testGetBalanceWithChargeHistory() {
        //given
        final long userId = 1L;
        final long pointHeld = 100L;

        Point point = Point.createInitBalance(userId);
        point.charge(pointHeld);

        when(pointQueryRepository.findByUserId(userId))
            .thenReturn(Optional.of(point));

        //when
        long balance = pointGetService.getBalance(userId);

        //then
        assertThat(balance).isEqualTo(pointHeld);
    }

}