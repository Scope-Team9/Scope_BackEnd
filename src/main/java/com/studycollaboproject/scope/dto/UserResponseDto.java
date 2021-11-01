package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class UserResponseDto {
    @Schema(description = "사용자 성향")
    private String userPropensityType;
    @Schema(description = "사용자의 선호 팀원 성향")
    private String memberPropensityType;
    @Schema(description = "닉네임")
    private String nickname;
    @Schema(description = "소개")
    private String introduction;
    @Schema(description = "기술 스택 리스트")
    private List<String> techStackList;


    public UserResponseDto(User user, List<String> techStackList){
        this.userPropensityType = user.getUserPropensityType();
        this.introduction = user.getIntroduction();
        this.memberPropensityType = user.getMemberPropensityType();
        this.nickname = user.getNickname();
        this.techStackList = techStackList;

    }
}
