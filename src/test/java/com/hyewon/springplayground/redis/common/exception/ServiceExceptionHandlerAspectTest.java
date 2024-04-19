package com.hyewon.springplayground.redis.common.exception;

import com.hyewon.springplayground.redis.dto.DummyDto;
import com.hyewon.springplayground.exception.ServiceExceptionHandlerAspect;
import com.hyewon.springplayground.redis.service.StockService;
import com.hyewon.springplayground.slack.SlackClient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.kafka.core.KafkaTemplate;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ServiceExceptionHandlerAspectTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private SlackClient slackClient;

    @Mock
    // ProceedingJoinPoint는 JoinPoint를 상속받아서 JoinPoint의 기능을 포함하고 있음
    private ProceedingJoinPoint proceedingJoinPoint;

    private AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    private ServiceExceptionHandlerAspect serviceExceptionHandlerAspect;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceExceptionHandlerAspect = new ServiceExceptionHandlerAspect(kafkaTemplate, slackClient);
    }

    @Test
    @DisplayName("handleServiceException 메서드 테스트")
    void handleServiceExceptionTest() throws Throwable {
        DummyDto dummyDto = new DummyDto();
        dummyDto.setName("test");
        dummyDto.setAge(30);

        // ProceedingJoinPoint의 getArgs() 메서드를 호출하면 Object[]를 반환하도록 설정
        when(proceedingJoinPoint.getArgs()).thenReturn(new Object[]{dummyDto});
        serviceExceptionHandlerAspect.handleServiceException(proceedingJoinPoint, new Exception("test exception"));

        // verify() 메서드를 이용해 kafkaTemplate.send()와 slackClient.sendSlack() 메서드가 각각 1회 호출되었는지 확인
        verify(kafkaTemplate, times(1)).send(anyString(), anyString());
        verify(slackClient, times(1)).sendSlack(anyString());
    }

    @Test
    @DisplayName("메소드단위로 포인트컷 표현식 테스트")
    void pointCutExpressionTest() throws NoSuchMethodException {
        Method serviceMethod = StockService.class.getMethod("createException");
        pointcut.setExpression("execution(* com.hyewon.springplayground.redis.service.StockService.createException(..))");
        assertTrue(pointcut.matches(serviceMethod, StockService.class));
    }

    @Test
    @DisplayName("패키지 단위로 포인트컷 표현식 테스트ㅂ")
    void pointCutExpressionTest2() throws NoSuchMethodException {
        Method serviceMethod = StockService.class.getMethod("createException");
        pointcut.setExpression("execution(* com.hyewon.springplayground.redis.service..*.*(..))");
        assertTrue(pointcut.matches(serviceMethod, StockService.class));
    }

    @Test
    void handleOtherExceptions() throws Throwable {
        serviceExceptionHandlerAspect.handleOtherExceptions(new Exception("test exception"));

        verify(kafkaTemplate, times(0)).send(anyString(), anyString());
        verify(slackClient, times(0)).sendSlack(anyString());
    }
}