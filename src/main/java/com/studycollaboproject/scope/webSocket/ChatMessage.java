package com.studycollaboproject.scope.webSocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessage {

    private String roomId;
    private String sender;
    private String msg;
}
