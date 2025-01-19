package kr.hhplus.be.server.domain.reservation.application;


import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import kr.hhplus.be.server.domain.reservation.domain.Reservation;
import kr.hhplus.be.server.domain.reservation.repository.ReservationRepository;
import kr.hhplus.be.server.interfaces.reservation.dto.ReservationRequest;
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
@Sql(scripts = "classpath:data_reservation.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class ReservationServiceConcurrencyTest {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;


    @DisplayName("하나의 좌석에 동시에 여러 번 좌석예약을 하는 경우, 하나의 예약 요청만 성공해야 한다.")
    @Test
    void testReserveOneSeatMultipleTimes() throws ExecutionException, InterruptedException {
        //given
        long userId = 1L;
        int count = 50;
        long seatId = 1L;
        ReservationRequest request = new ReservationRequest(userId, seatId);

        //비동기 요청 처리 준비
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final List<CompletableFuture<Boolean>> tasks = new ArrayList<>(count);
        final AtomicInteger exceptionCount = new AtomicInteger(0);

        //when
        for (int i = 0; i < count; i++) {
            tasks.add(CompletableFuture.supplyAsync(() -> {
                reservationService.reserve(request);
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
                .isEqualTo(count - 1);

            softAssertions.assertThat(finalSuccessCount)
                .as("성공한 요청 횟수 검증")
                .isEqualTo(1);

            softAssertions.assertThat(finalFailureCount)
                .as("실패한 요청 횟수 검증")
                .isEqualTo(exceptionCount.get());

            List<Reservation> reservations = reservationRepository.findAllById(seatId);
            softAssertions.assertThat(reservations)
                .as("해당 좌석 예약 수 검증")
                .hasSize(1);
        });
    }

}