package com.likelion.springpractice.week07;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

  private final ObjectMapper objectMapper;

  @Pointcut("execution(* com.likelion.springpractice.domain..controller..*(..))")
  public void controllerMethods(){
  }

  @Before("controllerMethods()")
  public void logRequest(JoinPoint joinPoint){
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

    String params = Arrays.stream(joinPoint.getArgs())
        .map(arg -> {
          try {
            return objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(arg);
          } catch (JsonProcessingException e){
            return String.valueOf(arg);
          }
        })
        .collect(Collectors.joining("\n"));

    log.info("[요청] {} {} | 파라미터(JSON): {}", request.getMethod(), request.getRequestURI(), params);
  }

  @AfterReturning(pointcut = "controllerMethods()", returning = "result")
  public void logResponse(JoinPoint joinPoint, Object result){
    String jsonResult;
    try {
      jsonResult= objectMapper
          .writerWithDefaultPrettyPrinter()
          .writeValueAsString(result);
    } catch (JsonProcessingException e) {
      jsonResult = String.valueOf(result);
    }
    MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
    log.info("[응답] {} 메서드 실행 완료 => 반환(JSON):\n{}", methodSignature.getName(), jsonResult);
  }

  @AfterThrowing(pointcut = "controllerMethods()", throwing = "ex")
  public void logException(JoinPoint joinPoint, Throwable ex){
    log.error("[예외 발생] {} | {}", joinPoint.getSignature(), ex.getMessage(), ex);
  }
}
