package com.belajar.springbootfilter.config;

import com.belajar.springbootfilter.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterRegistrationBean() {
        FilterRegistrationBean<JwtFilter> jwtFilterRegistrationBean = new FilterRegistrationBean<>();
        jwtFilterRegistrationBean.setFilter(jwtFilter);
        jwtFilterRegistrationBean.addUrlPatterns("/v1/profile");

        return jwtFilterRegistrationBean;
    }

}
