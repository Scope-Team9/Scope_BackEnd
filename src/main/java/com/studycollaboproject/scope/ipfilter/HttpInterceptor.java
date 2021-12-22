package com.studycollaboproject.scope.ipfilter;

import io.github.bucket4j.Bucket;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Log4j2
public class HttpInterceptor extends HandlerInterceptorAdapter {
    private final IpBanService ipBanService;

    public HttpInterceptor(IpBanService ipBanService){
        this.ipBanService = ipBanService;
    }

    PricingPlanService pricingPlanService = new PricingPlanService();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Bucket bucket = pricingPlanService.resolveBucket(request);

        if (ipBanService.isIpBanned(request)){
             log.warn("[{}] 제한된 접근입니다. request uri={}, client ip={}",MDC.get("UUID"), request.getRequestURI(), ipBanService.getIpAdress(request));
            return false;
        }

        if (bucket.tryConsume(1)) {
            // 1개 사용 요청
            // 초과하지 않음

            return true;
        } else {
            // 제한 초과
            String ip = ipBanService.getIpAdress(request);
            System.out.println("아이피" +ip);
            ipBanService.saveBanIp(ip);
            log.info(" [{}] 트래픽 초과 IP : {}", MDC.get("UUID"),request.getRemoteAddr());
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    }
}