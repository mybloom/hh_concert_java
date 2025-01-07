package kr.hhplus.be.server.domain.point.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.point.web.dto.PointChargeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Sql({"classpath:data.sql"})
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
        long userId = 1L;
        long chargeAmount = 1000L;
        long expectedBalance = 1000L;

        String requestBody = objectMapper.writeValueAsString(
            new PointChargeRequest(userId, chargeAmount));

        // When
        final ResultActions result = mockMvc.perform(post("/api/points")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpectAll(
            status().isOk(),
            jsonPath("$.data.balance").value(expectedBalance)
        ).andDo(print());
    }
}