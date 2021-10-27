package com.studycollaboproject.scope.model;

import com.studycollaboproject.scope.util.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Team extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
    private Post post;

    @Builder
    public Team(User user, Post post){
        this.post = post;
        this.user = user;
    }

    public void setUrl(String frontUrl, String backUrl){
        this.post.setUrl(frontUrl,backUrl);
    }
}
