package com.studycollaboproject.scope.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TechStack {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "techStack_id")
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Tech tech;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")

    private User user;

    public TechStack(Tech tech,User user){
        this.tech = tech;
        this.user = user;
        user.getTechStackList().add(this);
    }

    public TechStack(Tech tech,Post post){
        this.tech = tech;
        this.post = post;
        post.getTechStackList().add(this);
    }
}
