package com.studycollaboproject.scope.webSocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebSocketHandler extends TextWebSocketHandler {
    
    List<WebSocketSession> sessionList = new ArrayList<>();
    Map<String, WebSocketSession> userSessions = new HashMap<>();

    // hand shack 후 클라이언트 접속, 연결 시
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //현재 접속한 session 정보 저장
        Map<String ,Object> sessionVal = session.getAttributes();
        System.out.println("sessionVal = " + sessionVal);

        //session 정보에서 user 정보 추출

        //
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}
