package kr.hhplus.be.server.domain.queuetoken.web;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.domain.queuetoken.web.dto.TokenRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest
class QueueTokenControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("유효한 사용자 요청시 응답헤더에 x-queue-token 포함 확인")
    @Test
    void createTokeIncludeHeader() throws Exception {
    	//given
        long userId = 1L;
        TokenRequest request = new TokenRequest(userId);

        //when & then
        mockMvc.perform(post("/api/queue/token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(header().exists("x-queue-token"))
            .andExpect(jsonPath("$.data.tokenUuid").isNotEmpty())
            .andExpect(jsonPath("$.data.status").isNotEmpty());
    }
}