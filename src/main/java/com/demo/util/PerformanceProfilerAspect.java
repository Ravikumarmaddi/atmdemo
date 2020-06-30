package com.demo.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Aspect that implements automatic logging on performance of the data access queries with Spring Data MongoDB.
 *
 * @author Felix Jose
 */
@Aspect
@Component("PerformanceProfilerAspect")
public class PerformanceProfilerAspect {


    @Pointcut("execution(* com.demo.billpayments.repository.*.*(..))")
    public void clientMethodPointcut() {
    }

    /**
     * Log on the performance of the interactions/queries on MongoDB.
     *
     * @param joinPoint the join point
     * @throws Throwable the throwable
     */
    @Around("clientMethodPointcut()")
    public Object retryOnConnectionException(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret = null;

        System.out.println("PerformanceProfilerAspect: Advised with logic to calculate the Time Taken for the    execution of the method [" + joinPoint.getSignature() + "]");

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String throwableName = null;
        try {
            ret = joinPoint.proceed();

        } catch (Throwable t) {
            throwableName = t.getClass().getName();
            throw t;
        } finally {
            stopWatch.stop();
            if (throwableName != null) {
                System.out.println("Timed [" + joinPoint.getSignature().toString() + "]: " + stopWatch
                        .getTotalTimeMillis() + " milliseconds , with exception [" + throwableName + "]");
            } else {
                System.out.println("Timed [" + joinPoint.getSignature().toString() + "]: " + stopWatch
                        .getTotalTimeMillis() + " milliseconds");
            }
        }

        return ret;
    }
}