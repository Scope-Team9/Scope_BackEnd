package com.studycollaboproject.scope.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public
class TestUserSetupDto{
    private String snsId;
    private String nickname;
    private String memberPropensityType;
    private String userPropensityType;
    private String email;


    public TestUserSetupDto(){
        this.email = "unknown";
        this.memberPropensityType = "unknown";
        this.nickname = "unknown";
        this.userPropensityType = "unknown";
        this.snsId = "unknown";
    }
}