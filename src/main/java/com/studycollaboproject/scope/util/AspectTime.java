package com.studycollaboproject.scope.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class AspectTime
{
    @Around("execution(* com.studycollaboproject.scope.controller.*.*(..))")
    public Object executionAspect(ProceedingJoinPoint joinPoint) throws Throwable
    {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object result = joinPoint.proceed();

        stopWatch.stop();
        log.info("[{}], WorkTime : {} ms", MDC.get("UUID"), stopWatch.getLastTaskTimeNanos()/1000000.0);

        return result;
    }
}