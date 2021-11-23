package com.studycollaboproject.scope.model;

import com.studycollaboproject.scope.dto.MessageRequestDto;
import com.studycollaboproject.scope.util.Timestamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "Message_id")
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String receivedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sent_id")
    private User user;

    public Message(MessageRequestDto messageRequestDto, User user) {
        this.title = messageRequestDto.getTitle();
        this.message = messageRequestDto.getMessage();
        this.receivedId = messageRequestDto.getReceivedId();
        this.user = user;
    }

}
