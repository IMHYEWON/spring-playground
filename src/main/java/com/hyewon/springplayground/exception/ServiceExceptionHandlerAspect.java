package com.hyewon.springplayground.exception;

import com.hyewon.springplayground.redis.dto.DummyDto;
import com.hyewon.springplayground.slack.SlackClient;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
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
    public void handleServiceException(JoinPoint joinPoint, Exception ex) {
        // 서비스 레벨 예외 처리 로직
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            // 첫 번째 파라미터가 DTO 객체인 경우에만 전송
            Object firstArg = args[0];
            if (firstArg instanceof DummyDto) {
                DummyDto dto = (DummyDto) firstArg;
                kafkaTemplate.send("dead-letter-topic", dto.toString() + " - " + ex.getMessage());
                slackClient.sendSlack(dto.toString() + " - " + ex.getMessage());
            }
        }
        System.err.println("서비스 레벨에서 예외 발생: " + ex.getMessage());
    }

    @AfterThrowing(pointcut = "execution(* com.hyewon.springplayground.redis.repository..*.*(..))", throwing = "ex")
    public void handleOtherExceptions(Exception ex) {
        // 기타 예외 처리 로직
        System.err.println("레포지토리 레벨에서 예외 발생: " + ex.getMessage());
    }
}
