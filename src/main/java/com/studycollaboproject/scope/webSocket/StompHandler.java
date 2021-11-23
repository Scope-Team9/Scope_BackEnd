package com.studycollaboproject.scope.webSocket;

import com.studycollaboproject.scope.redis.ConnectedUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StompHandler implements ChannelInterceptor {

    private final AlertService alertService;

    //웹소켓에 요청이 들어오면 실행 됨
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        // CONNECT -  웹 소켓 연결 요청
        // SUBSCRIBE - 소켓에 메시지 요청
        // DISCONNECT - 로그아웃시 소켓 연결 종료
        if (StompCommand.CONNECT == accessor.getCommand()) {
            log.info("[CONNECT] 웹 소켓 연결 요청");
            // 연결시ㅣ 닉네임과 연결 경로 저장
            //accessor.getDestination() -> 로그인한 유저를 한 방에 몰아 넣을 거라 안바뀜
            //accessor.getUser().getName() -> UUID
            //accessor.getFirstNativeHeader("nickname") -> 프론트에게 받아야 하는 값 {nickname:닉네임값}
            ConnectedUser connectedUser = new ConnectedUser(accessor.getFirstNativeHeader("nickname"),accessor.getUser().getName());
            alertService.userConnect(connectedUser);

        } else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
            log.info("[SUBSCRIBE] 소켓에 메시지 요청");

        } else if (StompCommand.DISCONNECT == accessor.getCommand()) {
            log.info("[DISCONNECT] 로그아웃, 소켓 연결 종료");
            alertService.userDisConnect(accessor.getFirstNativeHeader("nickname"));
        }
        return message;
    }
}
