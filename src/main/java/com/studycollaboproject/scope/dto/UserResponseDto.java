package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.TechStack;
import com.studycollaboproject.scope.model.User;
import lombok.Getter;

import java.util.List;

@Getter
public class UserResponseDto {

    private String userPropensityType;

    private String memberPropensityType;

    private String nickname;

    private String introduction;

    private List<TechStack> techStackList;


    public UserResponseDto(User user){
        this.userPropensityType = user.getUserPropensityType();
        this.introduction = user.getIntroduction();
        this.memberPropensityType = user.getMemberPropensityType();
        this.nickname = user.getNickname();
        this.techStackList = user.getTechStackList();
    }
}
