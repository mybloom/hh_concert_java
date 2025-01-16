package kr.hhplus.be.server.domain.point.application;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import kr.hhplus.be.server.common.IntegrationTest;
import kr.hhplus.be.server.domain.point.repository.PointCommandRepository;
import kr.hhplus.be.server.domain.point.repository.PointQueryRepository;
import kr.hhplus.be.server.domain.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
class PointServiceConcurrencyTest extends IntegrationTest {

    @Autowired
    private PointChargeService pointChargeService;

    @Autowired
    private PointQueryRepository pointQueryRepository;

    @Autowired
    private PointCommandRepository pointCommandRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(new User());
//        pointCommandRepository.save(Point.createInitBalance(user.getId()));
    }

    @DisplayName("한 사용자가 여러 번 충전하는 경우, 충전 금액이 누적되어야 한다.")
    @Test
    void testUserChargesMultipleTimes() throws ExecutionException, InterruptedException {
        //given
        long userId = user.getId();
        long amount = 1000;
        int chargeCount = 30;

        //비동기 요청 처리 준비
        final List<CompletableFuture<Boolean>> tasks = new ArrayList<>(chargeCount);
        final AtomicInteger exceptionCount = new AtomicInteger(0);

        //when
        for (int i = 0; i < chargeCount; i++) {
            tasks.add(CompletableFuture.supplyAsync(() -> {
                pointChargeService.charge(userId, amount);
                return true;
            }).exceptionally(e -> {
                exceptionCount.incrementAndGet();
                return false;
            }));
        }

        //then
        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();

        int successCount = 0;
        int failureCount = 0;
        for (CompletableFuture<Boolean> task : tasks) {
            if (task.get()) {
                successCount++;
            } else {
                failureCount++;
            }
        }

        int finalSuccessCount = successCount;
        int finalFailureCount = failureCount;
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(exceptionCount.get())
                .as("예외 발생 횟수 검증")
                .isEqualTo(0);

            softAssertions.assertThat(finalSuccessCount)
                .as("성공한 요청 횟수 검증")
                .isEqualTo(chargeCount);

            softAssertions.assertThat(finalFailureCount)
                .as("실패한 요청 횟수 검증")
                .isEqualTo(exceptionCount.get());

            softAssertions.assertThat(pointQueryRepository.findByUserId(userId).get().getBalance())
                .as("최종 잔액 검증")
                .isEqualTo(amount * chargeCount);
        });
    }

}