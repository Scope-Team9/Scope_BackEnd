package com.studycollaboproject.scope.webSocket;

import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class Alert extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long alertId;

    @Column(nullable = false)
    private String alertMessage;

    @Column(nullable = false)
    private boolean alertChecked;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private AlertType alertType;

    @Column
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id", nullable = false)
    private User receivedUser;



    public Alert(String alertMessage, AlertType alertType, Long messageId, User receivedUser) {
        this.alertMessage = alertMessage;
        this.alertChecked = false;
        this.alertType = alertType;
        this.messageId = messageId;
        this.receivedUser = receivedUser;
    }

    public void alertReadCheck(){
        this.alertChecked = true;
    }
}
