package com.studycollaboproject.scope.webSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;


@RequiredArgsConstructor
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{

    private final WebSocketHandler webSocketHandler;

    //[공식문서] 내장 인터셉터를 사용해서 http 세션 속성을 websocket 세션에 전달
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //handshake 위한 주소 설정
        registry.addHandler(webSocketHandler,"/ws/my")
                .setAllowedOrigins("*");

    }
}
