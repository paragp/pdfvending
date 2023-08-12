package com.pdfvending.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.pdfvending.interceptor.TraceIdLoggingInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TraceIdLoggingInterceptor traceIdLoggingInterceptor;

    @Autowired
    public WebConfig(TraceIdLoggingInterceptor traceIdLoggingInterceptor) {
        this.traceIdLoggingInterceptor = traceIdLoggingInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(traceIdLoggingInterceptor);
    }
}
