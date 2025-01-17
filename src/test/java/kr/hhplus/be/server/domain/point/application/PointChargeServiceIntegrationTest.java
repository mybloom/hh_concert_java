package kr.hhplus.be.server.domain.point.application;

import static org.assertj.core.api.Assertions.assertThat;

import kr.hhplus.be.server.common.DataCleaner;
import kr.hhplus.be.server.domain.point.model.Point;
import kr.hhplus.be.server.domain.point.repository.PointCommandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointChargeServiceIntegrationTest {

    @Autowired
    private PointChargeService pointService;

    @Autowired
    private PointCommandRepository pointCommandRepository;

    @Autowired
    private DataCleaner dataCleaner;

    @BeforeEach
    void setUp() {
        dataCleaner.clean();
    }
    @DisplayName("충전한 이력이 있는 사용자가 100원을 충전한다.")
    @Test
    void chargeForExistingUser() {
        // given: 이미 포인트가 존재하는 사용자
        final long userId = 1L;
        final long pointHeld = 500L;
        final long pointCharge = 100L;

        Point existingPoint = Point.createInitBalance(userId);
        existingPoint.charge(pointHeld);
        pointCommandRepository.save(existingPoint);

        // when: 추가로 충전
        long newBalance = pointService.charge(userId, pointCharge);

        // then: 600원이 되어야 함
        assertThat(newBalance).isEqualTo(pointHeld + pointCharge);
    }

}