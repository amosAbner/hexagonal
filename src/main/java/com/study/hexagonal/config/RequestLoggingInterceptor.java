package com.study.hexagonal.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingInterceptor.class);
    private static final String REQUEST_ID_ATTRIBUTE = RequestLoggingInterceptor.class.getName() + ".requestId";
    private static final String START_TIME_ATTRIBUTE = RequestLoggingInterceptor.class.getName() + ".startTime";
    private static final String REQUEST_ID_HEADER = "X-Request-Id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        var requestId = UUID.randomUUID().toString();
        request.setAttribute(REQUEST_ID_ATTRIBUTE, requestId);
        request.setAttribute(START_TIME_ATTRIBUTE, System.nanoTime());
        MDC.put("requestId", requestId);
        response.setHeader(REQUEST_ID_HEADER, requestId);

        logger.info("HTTP request started: method={} path={}", request.getMethod(), request.getRequestURI());
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception exception
    ) {
        var requestId = (String) request.getAttribute(REQUEST_ID_ATTRIBUTE);
        var startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE);

        try {
            if (requestId == null || startTime == null) {
                return;
            }

            var durationMs = (System.nanoTime() - startTime) / 1_000_000;
            if (exception == null) {
                logger.info("HTTP request completed: method={} path={} status={} durationMs={}",
                        request.getMethod(), request.getRequestURI(), response.getStatus(), durationMs);
            } else {
                logger.warn("HTTP request failed: method={} path={} status={} durationMs={} exception={}",
                        request.getMethod(), request.getRequestURI(), response.getStatus(), durationMs,
                        exception.getClass().getSimpleName());
            }
        } finally {
            MDC.remove("requestId");
        }
    }
}
