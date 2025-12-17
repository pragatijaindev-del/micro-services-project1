package com.example.user_service.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

//Logging aspect for User service.Logs authentication related service calls and failure
@Aspect
@Component
public class AuthLoggingAspect {

    private static final Logger log =
            LoggerFactory.getLogger(AuthLoggingAspect.class);

    @Pointcut("execution(* com.example.user_service.service.*.*(..))")
    public void authServiceLayer() {}

    @Before("authServiceLayer()")
    public void logAuthAttempt(JoinPoint joinPoint) {
        log.info("User service started | Method: {} | Input: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterThrowing(pointcut = "authServiceLayer()", throwing = "ex")
    public void logAuthError(JoinPoint joinPoint, Exception ex) {
        log.error("User authentication failed | Method: {} | Reason: {}",
                joinPoint.getSignature().toShortString(),
                ex.getMessage(),
                ex);
    }
}
