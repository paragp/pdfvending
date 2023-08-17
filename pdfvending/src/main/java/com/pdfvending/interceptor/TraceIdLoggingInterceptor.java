/*
 * The interceptor generates a unique trace ID (using UUID) for every incoming web request and puts it in the MDC (Mapped Diagnostic Context) before handling the request.
After the request is processed, the interceptor removes the trace ID from the MDC to avoid memory leaks.
The postHandle method is not implemented and throws an UnsupportedOperationException.
 */
package com.pdfvending.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

import java.util.UUID;

@Component
public class TraceIdLoggingInterceptor implements WebRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TraceIdLoggingInterceptor.class);

    private static final String TRACE_ID = "traceId";

    @Override
    public void preHandle(WebRequest webRequest) throws Exception {
        // Generate trace ID
        String traceId = UUID.randomUUID().toString();
        // Put trace ID to MDC
        MDC.put(TRACE_ID, traceId);
        logger.info("*********************************************");
        logger.info("Generated trace ID: {}", traceId);

    }

    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) throws Exception {
        // Once the request is processed, remove trace ID from MDC to avoid memory leak
        MDC.remove(TRACE_ID);
        logger.info("Removed trace ID from MDC");
        logger.info("*********************************************");

    }

    @Override
    public void postHandle(WebRequest arg0, ModelMap arg1) throws Exception {
        // Intentionally left unimplemented as it's not required for the current
        // functionality
    }
}
