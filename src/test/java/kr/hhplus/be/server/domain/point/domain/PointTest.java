package kr.hhplus.be.server.domain.point.domain;

import static kr.hhplus.be.server.common.exception.IllegalArgumentErrorCode.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PointTest {

    /* 
     잔액 충전 시 검증
     잔액 충전 후 정상적으로 잔액이 증가한다.
     충전 금액이 0 또는 음수일 경우 IllegalArgumentException이 발생한다.
     */
    @DisplayName("충전 금액이 0일 경우 Exception 발생한다.")
    @Test
    void chargeZeroAmount() {
        //given
        Point point = new Point();
        point.setUserId(1L);

        //when&then
        assertThatThrownBy(() -> point.charge(0))
            .isInstanceOf(BusinessIllegalArgumentException.class)
            .hasMessageContaining(INVALID_CHARGE_AMOUNT.getMessage());
    }

    @DisplayName("충전 금액이 음수일 경우 Exception 발생한다.")
    @Test
    void chargeNegativeAmount() {
        //given
        Point point = new Point();
        point.setUserId(1L);

        //when
        assertThatThrownBy(() -> point.charge(-1))
            .isInstanceOf(BusinessIllegalArgumentException.class)
            .hasMessageContaining(INVALID_CHARGE_AMOUNT.getMessage());
    }

    @DisplayName("100원 충전 후 정상적으로 잔액이 증가한다")
    @Test
    void charge() {
        //given
        Point point = new Point();
        point.setUserId(1L); //todo: user를 point에 할당하는 것이 필요한가?(위 메서드도 포함해서)

        //when&then
        assertEquals(100, point.charge(100));
    }

    /* 잔액 사용 시 검증
     잔액 사용 후 정상적으로 잔액이 감소한다.
     사용 금액이 0 또는 음수일 경우 IllegalArgumentException이 발생한다.
     사용 후 잔액이 음수가 되면 IllegalArgumentException이 발생한다.*/
    @DisplayName("사용 금액이 0일 경우 Exception 발생한다.")
    @Test
    void useZeroAmount() {
        //given
        Point point = new Point();
        point.setUserId(1L);

        //when&then
        assertThatThrownBy(() -> point.use(0))
            .isInstanceOf(BusinessIllegalArgumentException.class)
            .hasMessageContaining(INVALID_USE_AMOUNT.getMessage());
    }

    @DisplayName("사용 후 잔액이 음수가 되면 Exception 발생한다.")
    @Test
    void useNegativeAmount() {
        //given
        long currentPoints = 10;
        long usePoints = 100;

        Point point = new Point();
        point.setUserId(1L);
        point.charge(currentPoints);

        //when&then
        assertThatThrownBy(() -> point.use(usePoints))
            .isInstanceOf(BusinessIllegalArgumentException.class)
            .hasMessageContaining(INSUFFICIENT_BALANCE.getMessage());
    }

}