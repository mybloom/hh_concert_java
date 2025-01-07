package kr.hhplus.be.server.domain.point.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PointServiceImplTest {

    @InjectMocks
    private PointServiceImpl pointService;

    @Mock
    private PointRepository pointRepository;

    @DisplayName("유저가 존재할 때 포인트 충전 잔액이 증가한다")
    @Test
    void chargeWhenUserExists() {
    	//given
        long userId = 1L;
        Point point = new Point();
        point.setUserId(userId);

        when(pointRepository.findByUserId(userId)).thenReturn(Optional.of(point));

        //when
        long amount = 100L;
        long balance = pointService.charge(userId, amount);

    	//then
        assertThat(balance).isEqualTo(100L);
        verify(pointRepository, times(1)).findByUserId(userId);
    }

}