package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Bookmark;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findAllByPostInAndUserNickname(List<Post> post, String nickname);
    List<Bookmark> findAllByUser(User user);

}
