package kr.hhplus.be.server.common.config.web;

import kr.hhplus.be.server.common.interceptor.QueueTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private QueueTokenInterceptor queueTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(queueTokenInterceptor)
            .addPathPatterns("/**")  // 모든 경로에 대해 인터셉터 적용
            .excludePathPatterns("/api/queue/tokens"); // 제외 경로
    }
}
