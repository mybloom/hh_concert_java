package kr.hhplus.be.server.interfaces.point.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import kr.hhplus.be.server.common.config.web.WebQueueTokenInterceptorConfig;
import kr.hhplus.be.server.domain.queuetoken.model.QueueToken;
import kr.hhplus.be.server.domain.queuetoken.repository.QueueTokenRepository;
import kr.hhplus.be.server.domain.user.domain.User;
import kr.hhplus.be.server.domain.user.repository.UserRepository;
import kr.hhplus.be.server.interfaces.point.dto.PointChargeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@Import(WebQueueTokenInterceptorConfig.class)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PointControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private QueueTokenRepository queueTokenRepository;

    @Autowired
    private UserRepository userRepository;

    private String tokenUuid;

    @BeforeEach
    void setup() {
        //DB에 user, token 저장
        final User user = userRepository.save(new User());

        tokenUuid = UUID.randomUUID().toString();
        final QueueToken queueToken = QueueToken.createActiveToken(user.getId(), tokenUuid);
        queueTokenRepository.save(queueToken);
    }

    @DisplayName("충전 요청 후 정상적으로 잔액이 반환된다.")
    @Test
    void testChargePoint() throws Exception {
        //given
        long userId = 1L;
        long chargeAmount = 1000L;

        String requestBody = objectMapper.writeValueAsString(new PointChargeRequest(userId, chargeAmount));

        //when
        final ResultActions result = mockMvc.perform(post("/api/points")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON)
            .header("x-queue-token", tokenUuid)
        );

        //then
        result.andExpectAll(
            status().isOk()
            , jsonPath("$.data.balance").value(chargeAmount)
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
            .header("x-queue-token", tokenUuid)
        );

        //then
        result.andExpectAll(
            status().isOk()
            , jsonPath("$.data.balance").value(expectedBalance)
        ).andDo(print());
    }
}