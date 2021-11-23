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
}
