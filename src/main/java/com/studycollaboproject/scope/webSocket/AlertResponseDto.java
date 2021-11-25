package com.studycollaboproject.scope.webSocket;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AlertResponseDto {

    private Long alertId;
    private String alertMessage;
    private boolean alertChecked;
    private AlertType alertType;
    private Long MessageId;
    private String messageDetail;
    private LocalDateTime createdAt;

    public AlertResponseDto(Alert alert,String messageDetail) {

        this.alertId = alert.getAlertId();
        this.alertMessage = alert.getAlertMessage();
        this.alertChecked = alert.isAlertChecked();
        this.alertType = getAlertType();
        this.MessageId = alert.getMessageId();
        this.messageDetail = messageDetail;
        this.createdAt = getCreatedAt();
    }

}
