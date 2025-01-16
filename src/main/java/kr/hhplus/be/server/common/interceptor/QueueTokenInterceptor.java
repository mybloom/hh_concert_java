package kr.hhplus.be.server.common.interceptor;

import static kr.hhplus.be.server.common.exception.errorcode.AuthenticationErrorCode.UNAUTHENTICATED_TOKEN;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.hhplus.be.server.common.exception.ErrorResponse;
import kr.hhplus.be.server.domain.queuetoken.application.QueueTokenValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
@Component
public class QueueTokenInterceptor implements HandlerInterceptor {

    private final ObjectMapper objectMapper;
    private final QueueTokenValidator queueTokenValidator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        String queueTokenUuid = request.getHeader("x-queue-token");

        if (queueTokenUuid == null || queueTokenUuid.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");

            ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.UNAUTHORIZED,
                UNAUTHENTICATED_TOKEN.getMessage()
            );
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            return false;
        }

        //queueToken 검증 로직
        return queueTokenValidator.isValidToken(queueTokenUuid);
    }

}
