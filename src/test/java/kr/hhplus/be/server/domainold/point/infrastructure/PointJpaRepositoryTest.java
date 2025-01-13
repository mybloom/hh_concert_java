package kr.hhplus.be.server.domainold.point.infrastructure;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;
import kr.hhplus.be.server.domainold.point.domain.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = "classpath:data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class PointJpaRepositoryTest {

    @Autowired
    private PointJpaRepository pointJpaRepository;

    private Point point;

    @DisplayName("유저가 존재할 때 포인트 정보를 조회한다")
    @Test
    void findByUserIdWhenUserExists() {
        Optional<Point> foundPoint = pointJpaRepository.findByUserId(1L);

        assertTrue(foundPoint.isPresent());
        assertEquals(1L, foundPoint.get().getUserId());
        assertEquals(0L, foundPoint.get().getBalance());
    }

    @DisplayName("포인트 충전 후 새로운 잔액이 반환된다")
    @Test
    void chargeWhenBalanceIsUpdated() {
        //given
        Optional<Point> beforePoint = pointJpaRepository.findByUserId(1L);
        long beforeBalance = beforePoint.get().getBalance();

        //when
        beforePoint.ifPresent(point -> {
            point.charge(100L);
            pointJpaRepository.save(point);
        });

        //then
        Optional<Point> afterPoint = pointJpaRepository.findByUserId(1L);
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(afterPoint)
                .isPresent()
                .hasValueSatisfying(point ->
                    softAssertions.assertThat(point.getBalance())
                        .isEqualTo(beforeBalance + 100L)
                );
        });
    }

}