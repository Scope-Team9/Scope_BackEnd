package com.studycollaboproject.scope.webSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;


@Configuration
@EnableWebSocketMessageBroker // websocket config, 웹소켓이 메시지 핸들링 가능하게 함
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer  {
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer , WebSocketConfigurer{

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/umm"); //prefix가 umm인 요청을 찾아서 message를 클라이언트에게 돌려주는걸 얘가 어떻게 다해
        registry.setApplicationDestinationPrefixes("/app"); // @MessageMapping 주소의 prefix 역할을 함
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/scope-websocket") // /scope-websocket을 주소로 가지는 앤드포인트 등록
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();
    }

    //[공식문서] 내장 인터셉터를 사용해서 http 세션 속성을 websocket 세션에 전달
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new WebSocketHandler(),"/myHandler")
                .addInterceptors(new HttpSessionHandshakeInterceptor());
    }
}
