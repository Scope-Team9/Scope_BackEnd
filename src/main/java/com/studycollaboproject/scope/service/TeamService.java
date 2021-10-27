package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.MemberListResponseDto;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    @Transactional
    public void acceptMember(Post post, User user, Boolean accept) {
        if(accept){
            Team team = Team.builder()
                    .user(user)
                    .post(post)
                    .build();
            teamRepository.save(team);
        }
    }

    public List<MemberListResponseDto> getMember(Long postId) {
        return teamRepository.findAllByPostId(postId)
                .stream().map(MemberListResponseDto::new).collect(Collectors.toCollection(ArrayList::new));
    }
}
