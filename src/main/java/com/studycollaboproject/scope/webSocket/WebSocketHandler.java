//package com.studycollaboproject.scope.webSocket;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//// 클라이언트들이 발송한 메시지들을 받아서 처리함
//@Component
//public class WebSocketHandler extends TextWebSocketHandler{
//
//    //로그인 한 인원 전체
//    List<WebSocketSession> sessionList = new ArrayList<>();
//    // 1:1로 할 경우
//    Map<String, WebSocketSession> userSessions = new HashMap<>();
//
//    //웹소켓이 연결되면 호출되는 메소드
//    //여기서 유저 정보를 받아서 session값이랑 map을 하든 set을 하든 해서 갖고 있어야 할 것 같은데 그걸 어떻게..?
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        // session id 계속 바뀜 근데? 로그인은 여러번 안하니까 상관 없지 않을까?
//        TextMessage t = new TextMessage("연결이 되었다."+session.getId());
//        session.sendMessage(t);
//    }
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        // 클라이언트가 보낸 정보 중 data만 추출
//        String payload = message.getPayload();
//        System.out.println("클라이언트가 보낸 메시지 = " + payload);
//        TextMessage textMessage = new TextMessage("서버가 냅다 보내는 메시지");
//
//        session.sendMessage(textMessage);
//    }
//}
