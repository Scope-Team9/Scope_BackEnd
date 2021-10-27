package com.studycollaboproject.scope.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studycollaboproject.scope.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        String uuid = UUID.randomUUID().toString();
        MDC.put("UUID", uuid);

        try {
//            String requestURI = httpRequest.getRequestURI();
//            String hostIp = httpRequest.getRemoteAddr();
//            log.info("REQUEST [{}][{}][{}]", uuid, hostIp, requestURI);
            chain.doFilter(request, response);
        } catch (RuntimeException e) {
            errorResponse((HttpServletResponse) response, e.getLocalizedMessage());
        }
    }
    private void errorResponse(HttpServletResponse response, String localizedMessage) throws IOException {
        ObjectMapper om = new ObjectMapper();

        //에러 메시지
        ResponseDto responseDto = new ResponseDto("failed", localizedMessage, "");

        //JSON 형식으로 변환
        String jsonString = om.writeValueAsString(responseDto);

        //JSON 형식, 한글 처리
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

        //출력
        out.print(jsonString);
        out.flush();
        out.close();
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
