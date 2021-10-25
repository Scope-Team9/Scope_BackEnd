package com.studycollaboproject.scope.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

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

    @Column(unique = true)
    private String email;

    private String userPropensityType;

    private String memberPropensityType;

    @Column(unique = true)
    private String nickname;

    private String introduction;

    private boolean emailReceiveAgreement;

    private boolean recommendationAgreement;

    private String memberTestResult;

    private String userTestResult;

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarkList;

    @OneToMany(mappedBy = "user")
    private List<Team> teamList;

    @OneToMany(mappedBy = "user")
    private List<TechStack> techStackList;
}
