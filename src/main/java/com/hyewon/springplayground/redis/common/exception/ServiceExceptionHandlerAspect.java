package com.hyewon.springplayground.redis.common.exception;

import com.hyewon.springplayground.slack.SlackClient;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ServiceExceptionHandlerAspect {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final SlackClient slackClient;

    @AfterThrowing(pointcut = "execution(* com.hyewon.springplayground.redis.service..*.*(..))", throwing = "ex")
    public void handleServiceException(Exception ex) {
        // 서비스 레벨 예외 처리 로직
        kafkaTemplate.send("dead-letter-topic", ex.getMessage());
        slackClient.sendSlack(ex.getMessage());
        System.err.println("서비스 레벨에서 예외 발생: " + ex.getMessage());
    }

    @AfterThrowing(pointcut = "execution(* com.hyewon.springplayground.redis.repository..*.*(..))", throwing = "ex")
    public void handleOtherExceptions(Exception ex) {
        // 기타 예외 처리 로직
        System.err.println("레포지토리 레벨에서 예외 발생: " + ex.getMessage());
    }
}
