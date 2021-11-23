package com.studycollaboproject.scope.model;

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

    @Column(nullable = false)
    private String sentId;
}
