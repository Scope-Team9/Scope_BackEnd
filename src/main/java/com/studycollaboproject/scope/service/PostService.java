package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.PostRepository;
import com.studycollaboproject.scope.repository.TeamRepository;
import com.studycollaboproject.scope.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public Post loadPostByPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );
    }

    public Post loadPostIfOwner(Long postId, Long userId) {
        return postRepository.findByIdAndUserId(postId, userId);
    }

    @Transactional
    public void updateUrl(String backUrl, String frontUrl, String nickname, Long postId){
        User user = userRepository.findByNickname(nickname).orElseThrow(
                ()-> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );
        Post post = postRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("해당 게시물을 찾을 수 없습니다.")
        );
        Team team = teamRepository.findByUserAndPost(user,post).orElseThrow(
                ()->new IllegalArgumentException("해당 게시물을 찾을 수 없습니다.")
        );
        team.setUrl(frontUrl, backUrl);

    }
}
