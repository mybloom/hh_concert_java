package kr.hhplus.be.server.domain.point.application;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import kr.hhplus.be.server.common.IntegrationTest;
import kr.hhplus.be.server.domain.point.repository.PointQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@Transactional
@SpringBootTest
@Sql(scripts = "classpath:data_point.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class PointChargeServiceConcurrencyTest extends IntegrationTest {

    @Autowired
    private PointChargeService pointChargeService;

    @Autowired
    private PointQueryRepository pointQueryRepository;

    @DisplayName("한 사용자가 여러 번 충전하는 경우, 충전 금액이 누적되어야 한다.")
    @Test
    void testUserChargesMultipleTimes() throws ExecutionException, InterruptedException {
        //given
        long userId = 1L;
        long amount = 1000;
        int chargeCount = 50;

        //비동기 요청 처리 준비
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final List<CompletableFuture<Boolean>> tasks = new ArrayList<>(chargeCount);
        final AtomicInteger exceptionCount = new AtomicInteger(0);

        //when
        for (int i = 0; i < chargeCount; i++) {
            tasks.add(CompletableFuture.supplyAsync(() -> {
                pointChargeService.charge(userId, amount);
                return true;
            }, executorService).exceptionally(e -> {
                exceptionCount.incrementAndGet();
                return false;
            }));
        }

        //then
        CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0])).join();
        //executor 종료
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

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