package kr.hhplus.be.server.common.config.web;

import kr.hhplus.be.server.common.interceptor.QueueTokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebQueueTokenInterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private QueueTokenInterceptor queueTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(queueTokenInterceptor)
            .addPathPatterns("/api/**")  // 모든 경로에 대해 인터셉터 적용
            .excludePathPatterns( //제외경로
                "/api/queue/tokens",
                "/swagger-ui/**",
                "/swagger-resources/**",
                "/v3/**",
                "/swagger/**",
                "/error"
            );
    }
}
