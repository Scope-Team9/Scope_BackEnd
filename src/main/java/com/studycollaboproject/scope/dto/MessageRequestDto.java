package com.studycollaboproject.scope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MessageRequestDto {

    String title;
    String message;
    String receivedNickname;
    String sentNickname;
}
