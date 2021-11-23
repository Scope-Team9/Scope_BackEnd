package com.studycollaboproject.scope.webSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;



@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
    private final CustomHandshakeHandler customHandshakeHandler;
    private final StompHandler stompHandler;


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
                .setAllowedOrigins("*")
                .setHandshakeHandler(customHandshakeHandler)
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");  // 해당 api를 구독하는 클라이언트에게 메세지 전송
        registry.setApplicationDestinationPrefixes("/pub"); // 클라이언트로부터 받을 prefix
    }

    // 유저 CONNECT와 DISCONNECT 할때의 정보를 가져올 수 있다.
    // 세션의 데이터는 StompHeaderAccessor를 통해서 얻어올 수 있다
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(stompHandler);
    }
}