package com.studycollaboproject.scope.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageRequestDto {

    String title;
    String message;
    String receivedId;
    String sentId;

    public MessageRequestDto(String title, String message, String receivedId, String sentId) {
        this.title = title;
        this.message = message;
        this.receivedId = receivedId;
        this.sentId = sentId;
    }
}
