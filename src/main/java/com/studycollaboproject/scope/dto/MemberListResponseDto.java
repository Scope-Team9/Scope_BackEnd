package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.Applicant;
import com.studycollaboproject.scope.model.PropensityType;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemberListResponseDto {

    private Long userId;
    private String nickname;
    private String email;
    private PropensityType userPropensityType;
    private LocalDateTime applicationDate;

    public MemberListResponseDto(Team team) {
        User user = team.getUser();
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.userPropensityType = user.getUserPropensityType();
        this.applicationDate = team.getCreatedAt();
    }

    public MemberListResponseDto(Applicant applicant) {
        User user = applicant.getUser();
        this.userId = user.getId();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.userPropensityType = user.getUserPropensityType();
        this.applicationDate = applicant.getCreatedAt();
    }
}
