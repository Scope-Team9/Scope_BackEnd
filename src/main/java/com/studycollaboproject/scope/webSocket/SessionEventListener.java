package com.studycollaboproject.scope.webSocket;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

@Component
public class SessionEventListener {
    @EventListener
    public void handleSessionConnected(SessionConnectedEvent event){
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(
                event.getMessage(),StompHeaderAccessor.class);
    }
}
