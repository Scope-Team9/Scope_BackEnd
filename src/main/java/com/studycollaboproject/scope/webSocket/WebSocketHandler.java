package com.studycollaboproject.scope.webSocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 클라이언트들이 발송한 메시지들을 받아서 처리함
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    
    List<WebSocketSession> sessionList = new ArrayList<>();
    Map<String, WebSocketSession> userSessions = new HashMap<>();

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 클라이언트가 보낸 정보 중 data만 추출
        String payload = message.getPayload();
        System.out.println("클라이언트가 보낸 메시지 = " + payload);
        TextMessage textMessage = new TextMessage("서버가 냅다 보내는 메시지");
        session.sendMessage(textMessage);
    }


}
