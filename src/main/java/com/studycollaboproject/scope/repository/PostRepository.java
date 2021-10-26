package com.studycollaboproject.scope.repository;

import com.studycollaboproject.scope.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestBody;

public interface PostRepository extends JpaRepository<Post,Long> {

}
