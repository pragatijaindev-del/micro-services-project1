package com.example.order_service.aop;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
// centralized logging aspect for order service to capture method entry,exit and execution time.
// it will reflact failure or error in the service layer
@Aspect
@Component
public class LoggingAndErrorAspect {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingAndErrorAspect.class);


    @Pointcut("execution(* com.example.order.service.*.*(..))")
    public void serviceLayer() {}


    @Before("serviceLayer()")
    public void logMethodStart(JoinPoint joinPoint) {
        log.info("order service started| Method: {} | Args: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }


    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void logMethodSuccess(JoinPoint joinPoint, Object result) {
        log.info("method execution is successful | Method: {} | Response: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }

    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void logMethodError(JoinPoint joinPoint, Exception ex) {
        log.error("service flow failed| Method: {} | Message: {}",
                joinPoint.getSignature().toShortString(),
                ex.getMessage(),
                ex);
    }


    @Around("serviceLayer()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed(); // actual method call

        long endTime = System.currentTimeMillis();

        log.info("order service execution time| Method: {} | Took: {} ms",
                joinPoint.getSignature().toShortString(),
                (endTime - startTime));

        return result;
    }
}
