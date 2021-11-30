package com.studycollaboproject.scope.ipfilter;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PricingPlanService {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    private String getHost(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("Host");
    } /* ---------------------- 접속 제한! ----------------- */

    public Bucket resolveBucket(HttpServletRequest httpServletRequest) {
        return cache.computeIfAbsent(httpServletRequest.getRequestURI(), this::newBucket);
    }

    private Bucket newBucket(String apiKey) {
        System.out.println("여기는" + apiKey);
        return Bucket4j.builder()
                // 10개의 클라이언트가 1초에 1000개씩 보낼 수 있는 대역폭
                .addLimit(Bandwidth.simple(100, Duration.ofMinutes(1)))
                .addLimit(Bandwidth.simple(20, Duration.ofSeconds(1)))
                .build();
    }
}