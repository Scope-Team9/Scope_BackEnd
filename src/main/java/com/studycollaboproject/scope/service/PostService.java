package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Post loadPostByPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );
    }

    public Post loadPostIfOwner(Long postId, Long userId) {
        return postRepository.findByIdAndUserId(postId, userId);
    }
}
