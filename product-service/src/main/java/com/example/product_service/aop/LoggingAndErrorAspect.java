package com.example.product_service.aop;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
//Centralized logging aspect for Product Service. Captures method entry, exit, execution time,and business failures.
@Aspect
@Component
public class LoggingAndErrorAspect {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingAndErrorAspect.class);


    @Pointcut("execution(* com.example.product_service.*.*(..))")
    public void productServiceLayer() {}


    @Before("productServiceLayer()")
    public void logMethodStart(JoinPoint joinPoint) {
        log.info("Product service flow started| Method: {} | Args: {}",
                joinPoint.getSignature().toShortString(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterReturning(pointcut = "productServiceLayer()", returning = "result")
    public void logMethodSuccess(JoinPoint joinPoint, Object result) {
        log.info("Product service execution completed | Method: {} | Response: {}",
                joinPoint.getSignature().toShortString(),
                result);
    }


    @AfterThrowing(pointcut = "productServiceLayer()", throwing = "ex")
    public void logMethodError(JoinPoint joinPoint, Exception ex) {
        log.error("Product service execution completed  | Method: {} | Message: {}",
                joinPoint.getSignature().toShortString(),
                ex.getMessage(),
                ex);
    }


    @Around("productServiceLayer()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        log.info("Product service execution completed  | Method: {} | Took: {} ms",
                joinPoint.getSignature().toShortString(),
                (end - start));

        return result;
    }
}
