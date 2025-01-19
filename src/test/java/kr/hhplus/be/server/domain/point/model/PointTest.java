package kr.hhplus.be.server.domain.point.model;



import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PointTest {

    @DisplayName("100원 충전 후 정상적으로 잔액이 증가한다")
    @Test
    void charge() {
        //given
        long userId = 1L;
        long chargeAmount = 100L;
        Point point = Point.createInitBalance(userId);

        //when
        long balance = point.charge(chargeAmount);

        //then
        assertThat(chargeAmount).isEqualTo(balance);
    }

}