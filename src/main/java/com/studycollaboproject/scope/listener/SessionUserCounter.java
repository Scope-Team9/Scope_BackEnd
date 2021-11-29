package com.studycollaboproject.scope.listener;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Component
@Slf4j
@Getter
public class SessionUserCounter implements HttpSessionListener {
    public static int count;

    public int getCount(){
        return count;
    }

    //세션이 만들어질 때 실행
    @Override
    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session =event.getSession();
        count++;
        log.info("세션 생성 : {}, 동시 접속자 : {}명",session.getId(),count);
    }

    //세션이 소멸될 때 실행행
   @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        if (count!=0){
            count--;
        }
       HttpSession session =event.getSession();
       log.info("세션 소멸 : {} 동시 접속자 : {}명",session.getId(),count);
    }
}
