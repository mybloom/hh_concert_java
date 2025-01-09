package kr.hhplus.be.server.domain.queuetoken.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import kr.hhplus.be.server.common.exception.BusinessIllegalArgumentException;
import kr.hhplus.be.server.common.exception.IllegalArgumentErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QueueTokenServiceTest {

    @Autowired
    private QueueTokenService queueTokenService;


    @DisplayName("존재하지 않는 token uuid이면, exception 반환한다")
    @Test
    void testExceptionNotExist() {
        //given
        String tokenUuid = "notExistUUID";

        //when
        assertThatThrownBy(() -> queueTokenService.isValidToken(tokenUuid))
            .isInstanceOf(BusinessIllegalArgumentException.class)
            .hasMessage(IllegalArgumentErrorCode.QUEUE_TOKEN_NOT_FOUND.getMessage());
    }

    @DisplayName("토큰상태가 INVALID인 경우, false 반환한다")
    @Test
    void testIsTokenInvalid() {
        //given
        String tokenUuid = "550e8400-e29b-41d4-a716-446655440011";

        //when
        boolean isValidToken = queueTokenService.isValidToken(tokenUuid);

        //then
        assertThat(isValidToken).isFalse();
    }

    @DisplayName("토큰상태가 ACTIVE이면서 만료처리시간이 지난 경우, false 반환한다")
    @Test
    void testExpirationTimePassed() {
        //given
        String tokenUuid = "550e8400-e29b-41d4-a716-446655440009";

        //when
        boolean isValidToken = queueTokenService.isValidToken(tokenUuid);

        //then
        assertThat(isValidToken).isFalse();
    }

    @DisplayName("토큰상태가 ACTIVE이면서 만료처리시간이 지나지 않은 경우, true 반환한다")
    @Test
    void testIsActiveButNotExpired() {
        //given
        String tokenUuid = "550e8400-e29b-41d4-a716-446655440008";

        //when
        boolean isValidToken = queueTokenService.isValidToken(tokenUuid);

        //then
        assertThat(isValidToken).isTrue();
    }

}