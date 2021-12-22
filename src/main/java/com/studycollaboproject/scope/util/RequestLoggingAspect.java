package com.studycollaboproject.scope.util;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Aspect
@Component
@Slf4j
public class RequestLoggingAspect {

    private String mapToString(Map<String, String[]> map) {
        StringBuilder result = new StringBuilder();
        result.append("[ ");
        List<Map.Entry<String, String[]>> entryList = new ArrayList<>(map.entrySet());
        for (Map.Entry<String, String[]> entry : entryList) {
            result.append(entry.getKey());
            result.append(":");
            result.append(Arrays.toString(entry.getValue()));

            result.append(", ");
        }
        result.append(" ]");
        return result.toString();
    }

    @Pointcut("within(com.studycollaboproject.scope.controller..*)")
    public void onRequest() {}

    @Around("com.studycollaboproject.scope.util.RequestLoggingAspect.onRequest()")
    public Object doLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        log.info("[{}], {}, {}, {}", MDC.get("UUID"), request.getRequestURI(), request.getMethod(), mapToString(request.getParameterMap()));

        Object result = proceedingJoinPoint.proceed();

        stopWatch.stop();
        log.info("[{}], {}, {}, WorkTime : {} ms", MDC.get("UUID"), request.getRequestURI(), request.getMethod(), stopWatch.getLastTaskTimeNanos()/1000000.0);

        return result;
    }
}
