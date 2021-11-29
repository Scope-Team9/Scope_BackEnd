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
public class Bookmark extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Bookmark(User user, Post post) {
        this.post = post;
        this.post.getBookmarkList().add(this);
        this.user = user;
        this.user.getBookmarkList().add(this);
    }

    public void delete() {
        this.post.getBookmarkList().remove(this);
        this.user.getBookmarkList().remove(this);
    }
}
