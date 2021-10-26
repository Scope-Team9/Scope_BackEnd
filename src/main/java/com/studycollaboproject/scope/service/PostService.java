package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.PostRepository;
import com.studycollaboproject.scope.repository.TeamRepository;
import com.studycollaboproject.scope.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    @Transactional
    public void updateUrl(String backUrl, String frontUrl,String nickname,Long postId){
        User user = userRepository.findByNickname(nickname);
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 게시글을 찾을 수 없습니다.")
        );
        Team team = teamRepository.findByUserAndPost(user,post).orElseThrow(
                ()-> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다")
        );
        team.setUrl(frontUrl,backUrl);



    }

}
