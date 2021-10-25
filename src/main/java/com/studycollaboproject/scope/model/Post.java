package com.studycollaboproject.scope.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "post_id")
    private Long id;

    private PostType postType;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private String summary;

    private String contents;

    private int totalMember;

    private int recruitmentMember;

    private ProjectStatus projectStatus;

    private boolean recommendationAgree;

    private String frontUrl;

    private String backUrl;

    private String userTestResult;

    private String memberTestResult;

    @OneToMany(mappedBy = "post")
    private List<Bookmark> bookmarkList;

    @OneToMany(mappedBy = "post")
    private List<Team> teamList;


}
