package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.PropensityType;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class TeamResponseDto {

    private Long userId;
    private String nickname;
    private String email;
    private PropensityType userPropensityType;
    private LocalDateTime applicationDate;

    public TeamResponseDto(Team team) {
        User user = team.getUser();
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.userPropensityType = user.getUserPropensityType();
        this.applicationDate = team.getCreatedAt();
    }
}
