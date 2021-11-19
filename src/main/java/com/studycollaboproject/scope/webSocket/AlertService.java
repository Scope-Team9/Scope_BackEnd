package com.studycollaboproject.scope.webSocket;

import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

@Service
public class AlertService {

    public <T> void sendMsg(WebSocketSession session, T message) {
    }

    public void userConnect(String accessorDestination, String userUuid, String nickname) {
        //유저 연결 정보 방(redis)에 저장
    }

    public void userDisConnect(String nickname) {
        //방(redis)에 있는 유저 연결 정보 삭제
    }

}
