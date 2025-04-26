package com.code.cetboot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author : IMG
 * @create : 2025/4/26
 */
@Aspect
@Component
public class LogAspect {

    private final Logger infoLogger = LoggerFactory.getLogger("com.code.cetboot.info");
    private final Logger errorLogger = LoggerFactory.getLogger("com.code.cetboot.error");

    @Pointcut("execution(* com.code.cetboot.controller.*.*(..))")
    public void log() {}

    @Before("log()")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        infoLogger.info("URL={},method={},ip={},class_method={},args={}",
                request.getRequestURI(),
                request.getMethod(),
                request.getRemoteAddr(),
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                joinPoint.getArgs());
    }

    @AfterReturning("log()")
    public void afterReturning(JoinPoint joinPoint) {
        infoLogger.info("class_method={} return successfully",
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
    }
    @AfterThrowing(pointcut = "log()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Throwable exception) {
        errorLogger.error("class_method={} threw exception: {}",
                joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName(),
                exception.getMessage(), exception);
    }
}
