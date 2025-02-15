package kr.hhplus.be.server.interfaces.concert.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@Sql("classpath:data_concert.sql")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ConcertControllerTest {


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("유효한 concertId와 page 번호를 전달하면, 콘서트의 공연날짜를 조회한다")
    @Test
    void retrieveConcertSchedules() throws Exception {
        //given
        long concertId = 1L;
        Integer page = 1;

        //when
        final ResultActions result = mockMvc.perform(get("/api/concerts/"+concertId+"/schedules")
            .param("page", String.valueOf(page))
            .contentType(MediaType.APPLICATION_JSON)
            .header("x-queue-token", "550e8400-e29b-41d4-a716-446655440001"));

        //then
        result.andExpectAll(
            status().isOk()
        ).andDo(print());
    }

}