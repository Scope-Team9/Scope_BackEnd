package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.Message;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageResponseDto {
    String title;
    String message;
    String receivedNickname;
    String sentNickname;

    public MessageResponseDto(Message message) {
        this.title = message.getTitle();
        this.message = message.getMessage();
        this.receivedNickname = message.getReceivedNickname();
        this.sentNickname = message.getUser().getNickname();
    }
}
