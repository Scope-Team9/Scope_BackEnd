package com.studycollaboproject.scope.webSocket;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class ChatRoom {

    private String roomId;
    private String name;

    ChatRoom(String name){
        this.roomId = UUID.randomUUID().toString();
        this.name=name;
    }


}
