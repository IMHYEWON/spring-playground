package com.hyewon.springplayground.trace;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoggingController {

    private static final Logger logger = LoggerFactory.getLogger(LoggingController.class);

    @GetMapping("/test-log")
    public String logTest() {
        // MDC에 traceId와 spanId를 설정
        log.info("String message sent at {}", System.currentTimeMillis());
        log.info("MDC traceId: {}", MDC.get("traceId"));
        log.info("Current MDC: {}", MDC.getCopyOfContextMap());
        log.info("getMDCAdapter: {}", MDC.getMDCAdapter());
        log.info("popByKey traceId: {}", MDC.popByKey("traceId"));
//        Span currentSpan = Span.current();
//        if (currentSpan != null && currentSpan.getSpanContext().isValid()) {
//            SpanContext context = currentSpan.getSpanContext();
//            log.info("traceId from Span: {}", context.getTraceId());
//            log.info("spanId from Span: {}", context.getSpanId());
//        } else {
//            log.info("No active span found");
//        }
        return "Check your logs for traceId and spanId!";
    }
}
