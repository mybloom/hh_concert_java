package kr.hhplus.be.server.interfaces.reservation;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.common.DataCleaner;
import kr.hhplus.be.server.domain.reservation.application.result.PaymentRequest;
import kr.hhplus.be.server.domain.reservation.domain.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DataCleaner dataCleaner;

    @BeforeEach
    void setUp() {
        dataCleaner.clean();
    }


    @DisplayName("존재하지 않는 reservationId로 결제 요청 시 예외 발생")
    @Test
    void payWithInvalidReservationId() throws Exception {
        // Given
        long invalidReservationId = 999L;
        PaymentRequest request = new PaymentRequest(invalidReservationId, 1L);
        String requestBody = objectMapper.writeValueAsString(request);

        // When
        ResultActions result = mockMvc.perform(post("/api/payments")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON)
            .header("x-queue-token", "550e8400-e29b-41d4-a716-446655440001")
        );

        // Then
        result.andExpect(status().isBadRequest())
//            .andExpect(jsonPath("$.message").value("INVALID_RESERVATION_ID"))
            .andDo(print());
    }

    @DisplayName("예약한지 5분이내의 정상적인 예약id로 결제 요청은 결제에 성공한다")
    @Test
    void payWithValidReservationId() throws Exception {
        // Given
        long seatId = 1L;
        long userId = 1L;
        Reservation reservation = Reservation.createTempReservation(seatId, userId);

        ReservationRequest reservationRequest = new ReservationRequest(userId, seatId);
        String reservationBody = objectMapper.writeValueAsString(reservationRequest);
        MvcResult reservationResult = mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reservationBody)
                .header("x-queue-token", "550e8400-e29b-41d4-a716-446655440001"))
            .andExpect(status().isOk())
            .andReturn();

        // JSON에서 reservationId 추출
        String reservationResponse = reservationResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(reservationResponse);
        long reservationId = jsonNode.get("data").get("reservationId").asLong();

        // 2. 결제 API 호출 (방금 생성한 예약 ID로 결제 요청)
        PaymentRequest paymentRequest = new PaymentRequest(reservationId, userId);
        String paymentBody = objectMapper.writeValueAsString(paymentRequest);

        ResultActions result = mockMvc.perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(paymentBody)
            .header("x-queue-token", "550e8400-e29b-41d4-a716-446655440001"));

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.data.reservationId").value(reservationId))
            .andDo(print());
    }
}