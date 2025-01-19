package kr.hhplus.be.server.common.filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws IOException, ServletException {

        // 요청과 응답을 캐싱 가능한 래퍼로 감싸기
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis(); // 요청 시작 시간 기록

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse); // 다음 필터 또는 서블릿 실행
        } finally {
            long duration = System.currentTimeMillis() - startTime; // 실행 시간 계산
            logRequest(wrappedRequest, duration);
            logResponse(wrappedResponse);
            wrappedResponse.copyBodyToResponse(); // 응답 본문을 클라이언트로 전달
        }
    }

    private void logRequest(ContentCachingRequestWrapper request, long duration) {
        try {
            String requestBody = new String(request.getContentAsByteArray());
            String logMessage = String.format(
                "\n=== HTTP Request ===\n" +
                    "Method: %s\n" +
                    "URI: %s\n" +
                    "Query: %s\n" +
                    "Headers: %s\n" +
                    "Body: %s\n" +
                    "Execution Time: %d ms\n" +
                    "===================",
                request.getMethod(),
                request.getRequestURI(),
                request.getQueryString(),
                getHeadersAsString(request),
                requestBody.isEmpty() ? "No Body" : requestBody,
                duration
            );
            logger.info(logMessage);
        } catch (Exception e) {
            logger.error("Failed to log request: {}", e.getMessage(), e);
        }
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        try {
            String responseBody = new String(response.getContentAsByteArray());
            String logMessage = String.format(
                "\n=== HTTP Response ===\n" +
                    "Status: %d\n" +
                    "Headers: %s\n" +
                    "Body: %s\n" +
                    "=====================",
                response.getStatus(),
                getHeadersAsString(response),
                responseBody.isEmpty() ? "No Body" : responseBody
            );
            logger.info(logMessage);
        } catch (Exception e) {
            logger.error("Failed to log response: {}", e.getMessage(), e);
        }
    }

    private String getHeadersAsString(HttpServletRequest request) {
        StringBuilder headers = new StringBuilder();
        request.getHeaderNames().asIterator().forEachRemaining(headerName -> {
            headers.append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
        });
        return headers.toString().trim();
    }

    private String getHeadersAsString(HttpServletResponse response) {
        StringBuilder headers = new StringBuilder();
        response.getHeaderNames().forEach(headerName -> {
            headers.append(headerName).append(": ").append(response.getHeader(headerName)).append("\n");
        });
        return headers.toString().trim();
    }
}
