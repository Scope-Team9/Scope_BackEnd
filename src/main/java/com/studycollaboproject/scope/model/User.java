package com.studycollaboproject.scope.model;

import com.studycollaboproject.scope.dto.SignupRequestDto;
import com.studycollaboproject.scope.dto.SignupTestDto;
import com.studycollaboproject.scope.dto.TestUserSetupDto;
import com.studycollaboproject.scope.util.Timestamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Timestamped {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(unique = true)
    private String snsId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String userPropensityType;

    @Column(nullable = false)
    private String memberPropensityType;

    @Column(unique = true, nullable = false)
    private String nickname;

    @Column(length = 5000)
    private String introduction;

    @Column(unique = true,nullable = false)
    private String mailAuthenticationCode;

    private boolean isVerifiedEmail;


    private boolean emailReceiveAgreement;

//    @Column(nullable = false)
    private boolean recommendationAgreement;

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Team> teamList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<TechStack> techStackList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Applicant> applicantList = new ArrayList<>();

    public User(SignupRequestDto signupRequestDto, String userTestResult, String memberTestResult){
        this.email =signupRequestDto.getEmail();
        this.snsId = signupRequestDto.getSnsId();
        this.nickname = signupRequestDto.getNickname();
        this.userPropensityType = userTestResult;
        this.memberPropensityType = memberTestResult;
        this.isVerifiedEmail = false;
        this.emailReceiveAgreement = false;
        this.introduction = "";
    }
    public User(SignupTestDto signupTestDto, String token){
        this.email =signupTestDto.getEmail();
        this.snsId = signupTestDto.getSnsId();
        this.nickname = signupTestDto.getNickname();
        this.userPropensityType = signupTestDto.getUserPropensityType();
        this.memberPropensityType = signupTestDto.getMemberPropensityType();
        this.introduction = "";
        this.isVerifiedEmail = false;
        this.emailReceiveAgreement = false;
        this.mailAuthenticationCode = token;
    }
    public User(String snsId,String userPropensityType,String memberPropensityType, String nickname,String email){
        this.snsId = snsId;
        this.email = email;
        this.userPropensityType = userPropensityType;
        this.memberPropensityType = memberPropensityType;
        this.nickname = nickname;
        this.introduction = "";

    }

    public User(TestUserSetupDto testUserSetupDto) {
        this.nickname = testUserSetupDto.getNickname();
        this.userPropensityType = testUserSetupDto.getUserPropensityType();
        this.email = testUserSetupDto.getEmail();
        this.snsId = testUserSetupDto.getSnsId();
        this.memberPropensityType = testUserSetupDto.getMemberPropensityType();
        this.introduction = "";
        this.isVerifiedEmail = false;
        this.emailReceiveAgreement = false;
        this.mailAuthenticationCode = UUID.randomUUID().toString();
    }

    public void addTechStackListAndToken(List<TechStack> techStackList, String token){
        this.mailAuthenticationCode = token.split("\\.")[1];
        this.techStackList = techStackList;
    }

    public String updateUserPropensityType(String propensityResult) {
        this.userPropensityType = propensityResult;
        return this.userPropensityType;
    }

    public String updateMemberPropensityType(String propensityResult) {
        this.memberPropensityType = propensityResult;
        return this.memberPropensityType;
    }

    public void resetTechStack(){
        this.techStackList = new ArrayList<>();
    }

    public void updateUserInfo(String email, String nickname,List<TechStack> techStackList){
        this.email = email;
        this.nickname = nickname;
        this.techStackList = techStackList;
    }

    public void updateUserInfo(String userDesc) {
        this.introduction = userDesc;
    }

    public void verifiedEmail(){
        this.isVerifiedEmail=true;
        this.emailReceiveAgreement = true;
    }
}
