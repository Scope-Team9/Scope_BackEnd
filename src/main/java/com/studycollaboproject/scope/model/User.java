package com.studycollaboproject.scope.model;

import com.studycollaboproject.scope.dto.SignupRequestDto;
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
    private String kakaoId;

    @Column(unique = true)
    private String githubId;

    @Column(unique = true)
    private String googleId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private PropensityType userPropensityType;

    @Column(nullable = false)
    private PropensityType memberPropensityType;

    @Column(unique = true, nullable = false)
    private String nickname;

    private String introduction;

    @Column(nullable = false)
    private boolean emailReceiveAgreement;

    @Column(nullable = false)
    private boolean recommendationAgreement;

    @Column(nullable = false)
    private String memberTestResult;

    @Column(nullable = false)
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
        this.githubId = signupRequestDto.getGithubId();
        this.googleId = signupRequestDto.getGoogleId();
        this.kakaoId = signupRequestDto.getKakaoId();
        this.nickname = signupRequestDto.getNickname();
    }
    public void addTechStack(TechStack techStack){
        this.techStackList.add(techStack);
    }

    public PropensityType updateUserPropensityType(String propensityResult) {
        this.userPropensityType = PropensityType.valueOf(propensityResult);

        return this.userPropensityType;
    }

    public PropensityType updateMemberPropensityType(String propensityResult) {
        this.memberPropensityType = PropensityType.valueOf(propensityResult);

        return this.memberPropensityType;
    }
}
