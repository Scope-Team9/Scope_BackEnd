package com.studycollaboproject.scope.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String uuid = UUID.randomUUID().toString();
        MDC.put("UUID", uuid);

//            String requestURI = httpRequest.getRequestURI();
//            String hostIp = httpRequest.getRemoteAddr();
//            log.info("REQUEST [{}][{}][{}]", uuid, hostIp, requestURI);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
