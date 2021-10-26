package com.studycollaboproject.scope.model;

import com.studycollaboproject.scope.util.Timestamped;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private Long kakaoId;

    @Column(unique = true)
    private Long githubId;

    @Column(unique = true)
    private Long googleId;

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
    private List<Bookmark> bookmarkList;

    @OneToMany(mappedBy = "user")
    private List<Team> teamList;

    @OneToMany(mappedBy = "user")
    private List<TechStack> techStackList;

    @OneToMany(mappedBy = "user")
    private List<Applicant> applicantList;
}
