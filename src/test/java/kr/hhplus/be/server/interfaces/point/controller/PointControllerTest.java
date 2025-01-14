package kr.hhplus.be.server.interfaces.point.controller;


import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.hhplus.be.server.common.config.web.WebQueueTokenInterceptorConfig;
import kr.hhplus.be.server.common.interceptor.QueueTokenInterceptor;
import kr.hhplus.be.server.domain.point.application.PointChargeService;
import kr.hhplus.be.server.domain.point.application.PointGetService;
import kr.hhplus.be.server.interfaces.point.dto.PointChargeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


@WebMvcTest(
    value = PointController.class,
    // @ComponentScan.Filter : 특정 빈 제외, FilterType.ASSIGNABLE_TYPE : 클래스 타입 기준 필터링, classes : 제외할 클래스
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = {WebQueueTokenInterceptorConfig.class, QueueTokenInterceptor.class}
        )
    }
)
class PointControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PointChargeService pointChargeService;

    @MockitoBean
    private PointGetService pointGetService;


    @DisplayName("포인트 충전: 유효한 요청 시 httpStatus 200 반환")
    @Test
    void whenValidRequest_thenReturn200() throws Exception {
        //given
        long userId = 1L;
        long amount = 100L;
        PointChargeRequest request =
            PointChargeRequest.builder()
                .userId(userId)
                .amount(amount)
                .build();
        String requestBody = objectMapper.writeValueAsString(request);

        when(pointChargeService.charge(anyLong(), anyLong())).thenReturn(amount);

        //when
        final ResultActions result = mockMvc.perform(post("/api/points")
            .content(requestBody)
            .contentType(MediaType.APPLICATION_JSON));

        //then
        result.andExpectAll(
            status().isOk()
            , jsonPath("$.data.balance").value(amount)
        ).andDo(print());
    }

    @DisplayName("포인트 충전: 0원 충전 요청 시 httpStatus 400 반환")
    @Test
    void whenInValidRequest_thenReturn400() throws Exception {
        //given
        long userId = 1L;
        long amount = 0L;
        PointChargeRequest request =
            PointChargeRequest.builder()
                .userId(userId)
                .amount(amount)
                .build();
        String requestBody = objectMapper.writeValueAsString(request);

        //when
        final ResultActions result = mockMvc.perform(post("/api/points")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestBody));

        //then
        result.andExpectAll(
            status().isBadRequest()
        ).andDo(print());
    }

}