package com.studycollaboproject.scope.model;

import com.studycollaboproject.scope.dto.SignupRequestDto;
import com.studycollaboproject.scope.dto.UserRepuestDto;
import com.studycollaboproject.scope.util.Timestamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private String introduction;

//    @Column(nullable = false)
    private boolean emailReceiveAgreement;

//    @Column(nullable = false)
    private boolean recommendationAgreement;

//    @Column(nullable = false)
    private String memberTestResult;

//    @Column(nullable = false)
    private String userTestResult;

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Team> teamList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<TechStack> techStackList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Applicant> applicantList = new ArrayList<>();

    public User(SignupRequestDto signupRequestDto){
        this.email =signupRequestDto.getEmail();
        this.snsId = signupRequestDto.getSnsId();
        this.nickname = signupRequestDto.getNickname();
    }
    public void addTechStack(TechStack techStack){
        this.techStackList.add(techStack);
    }

    public void addTechStackList(List<TechStack> techStackList){
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
}
