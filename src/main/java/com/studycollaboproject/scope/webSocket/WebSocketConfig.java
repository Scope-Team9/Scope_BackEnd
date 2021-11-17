package com.studycollaboproject.scope.webSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;


@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    private final CustomHandshakeHandler handshakeHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("ws/scope")
                .setHandshakeHandler(handshakeHandler)
                .setAllowedOrigins("*");
        System.out.println("registry = " + registry);
        System.out.println(registry.getClass().getName());
    }

}