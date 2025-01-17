package kr.hhplus.be.server.common.config.filter;

import kr.hhplus.be.server.common.filter.LoggingFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

public class FilterConfig {
    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilter() {
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoggingFilter());
        registrationBean.addUrlPatterns("/*"); // 모든 URL에 대해 적용
        registrationBean.setOrder(1); // 필터의 순서를 지정 (낮은 숫자가 우선)
        return registrationBean;
    }
}
