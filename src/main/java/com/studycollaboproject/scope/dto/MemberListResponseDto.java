package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.Applicant;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class MemberListResponseDto {

    @Schema(description = "유저 ID")
    private Long userId;
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "Email")
    private String email;
    @Schema(description = "유저 성향")
    private String  userPropensityType;
    @Schema(description = "지원 날짜")
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
