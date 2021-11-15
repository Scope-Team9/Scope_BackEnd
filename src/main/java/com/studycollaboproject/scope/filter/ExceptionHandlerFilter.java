package com.studycollaboproject.scope.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.studycollaboproject.scope.dto.ResponseDto;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if(request.getContentLengthLong() > 10000) {
                errorResponse(response, "Request content exceed limit of 10000 bytes");
                return ;
            }
            filterChain.doFilter(request, response);
        } catch (RuntimeException exception) {
            errorResponse(response, exception.getLocalizedMessage());
        }
    }

    private void errorResponse(HttpServletResponse response, String localizedMessage) throws IOException {
        ObjectMapper om = new ObjectMapper();

        //에러 메시지
        ResponseDto responseDto = new ResponseDto(localizedMessage, "");

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
}
