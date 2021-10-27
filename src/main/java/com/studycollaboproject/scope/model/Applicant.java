package com.studycollaboproject.scope.model;

import com.studycollaboproject.scope.util.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Applicant extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicant_id")
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @Builder
    public Applicant(User user, Post post, String comment){
        this.post = post;
        post.getApplicantList().add(this);
        this.user = user;
        user.getApplicantList().add(this);
        this.comment = comment;
    }

    public void deleteApply() {
        this.user.getApplicantList().remove(this);
        this.post.getApplicantList().remove(this);
    }
}
