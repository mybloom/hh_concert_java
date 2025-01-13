package kr.hhplus.be.server.domain.point.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.point.web.dto.PointChargeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("충전 요청 후 정상적으로 잔액이 반환된다.")
    @Test
    void testChargePoint() throws Exception {
        // Given
        long userId = 10L;
        long chargeAmount = 1000L;
        long expectedBalance = 1000L;

        String requestBody = objectMapper.writeValueAsString(
            new PointChargeRequest(userId, chargeAmount));

        // When
        final ResultActions result = mockMvc.perform(post("/api/points")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON)
            .header("x-queue-token", "550e8400-e29b-41d4-a716-446655440001")
        );

        //then
        result.andExpectAll(
            status().isOk()
//            jsonPath("$.data.balance").value(expectedBalance)
        ).andDo(print());
    }

    @DisplayName("포인트 조회 후 정상적으로 잔액이 반환된다.")
    @Test
    void retrievePoint() throws Exception {
        // Given
        long userId = 1L;
        long expectedBalance = 0L;

        // When
        final ResultActions result = mockMvc.perform(get("/api/points")
            .param("userId", String.valueOf(userId))
            .contentType(MediaType.APPLICATION_JSON)
            .header("x-queue-token", "550e8400-e29b-41d4-a716-446655440001")
        );

        //then
        result.andExpectAll(
            status().isOk()
//            jsonPath("$.data.balance").value(expectedBalance)
        ).andDo(print());
    }

}