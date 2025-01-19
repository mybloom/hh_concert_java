package kr.hhplus.be.server.interfaces.queuetoken.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import kr.hhplus.be.server.common.IntegrationTest;
import kr.hhplus.be.server.domain.queuetoken.model.QueueOffset;
import kr.hhplus.be.server.domain.queuetoken.model.QueueToken;
import kr.hhplus.be.server.domain.user.domain.User;
import kr.hhplus.be.server.interfaces.queuetoken.dto.TokenRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QueueTokenIntegrationTest extends IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        dataCleaner.clean();

        //DB에 user, tokenOffset 저장
        final User user = userRepository.save(new User());
        queueOffsetRepository.save(QueueOffset.of(0L));
    }

    @DisplayName("유효한 사용자 요청시 응답헤더에 x-queue-token 포함 확인")
    @Test
    void createTokeIncludeHeader() throws Exception {
        //given
        long userId = 1L;
        TokenRequest request = new TokenRequest(userId);

        //when & then
        mockMvc.perform(post("/api/queue/tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(header().exists("x-queue-token"))
            .andExpect(jsonPath("$.data.tokenUuid").isNotEmpty())
            .andExpect(jsonPath("$.data.status").isNotEmpty());
    }

    @DisplayName("서버 임계 도달 전, ACTIVE 상태 토큰이 발급된다. "
        + "1.응답헤더에 x-queue-token 포함 확인"
        + "2.응답바디에 status = ACTIVE 확인"
    )
    @Test
    void createActiveTokeSuccess() throws Exception {
        //given
        long userId = 1L;
        TokenRequest request = new TokenRequest(userId);

        //when & then
        mockMvc.perform(post("/api/queue/tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(header().exists("x-queue-token"))
            .andExpect(jsonPath("$.data.tokenUuid").isNotEmpty())
            .andExpect(jsonPath("$.data.status").value("ACTIVE"));
    }

    @DisplayName("서버 임계 도달 후, WAIT 상태 토큰이 발급된다. "
        + "1.응답헤더에 x-queue-token 포함 확인"
        + "2.응답바디에 status = WAIT 확인"
    )
    @Test
    void createWaitTokeSuccess() throws Exception {
        //given
        Long userId = setupForWaitTokenTest();

        TokenRequest request = new TokenRequest(userId);

        //when & then
        mockMvc.perform(post("/api/queue/tokens")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(header().exists("x-queue-token"))
            .andExpect(jsonPath("$.data.tokenUuid").isNotEmpty())
            .andExpect(jsonPath("$.data.status").value("WAIT"));
    }

    private Long setupForWaitTokenTest() {
        //사용자 생성
        User user1 = userRepository.save(new User());
        User user2 = userRepository.save(new User());
        User user3 = userRepository.save(new User());

        //임계값 도달: 토큰 2개를 대기열에 넣는다.
        final String tokenUuid = UUID.randomUUID().toString();
        final QueueToken queueToken = QueueToken.createActiveToken(
            user1.getId(), tokenUuid, queueTokenProperties.getInitExpirationMinutes()
        );
        queueTokenRepository.save(queueToken); // 임계값 도달

        final String tokenUuid2 = UUID.randomUUID().toString();
        final QueueToken queueToken2 = QueueToken.createActiveToken(
            user2.getId(), tokenUuid2, queueTokenProperties.getInitExpirationMinutes()
        );
        queueTokenRepository.save(queueToken2); // 임계값 도달

        //offset 증가
        queueOffsetRepository.findById(1L)
            .ifPresent(queueOffset -> {
                queueOffset.setLastActiveOffset(2);
                queueOffsetRepository.save(queueOffset);
            });

        return user3.getId();
    }

}