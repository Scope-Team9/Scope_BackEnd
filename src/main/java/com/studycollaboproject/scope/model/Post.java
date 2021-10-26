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

    @Column(nullable = false)
    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private int totalMember;

    @Column(nullable = false)
    private int recruitmentMember;

    @Column(nullable = false)
    private ProjectStatus projectStatus;

    @Column(nullable = false)
    private boolean recommendationAgree;

    private String frontUrl;

    private String backUrl;

    @Column(nullable = false)
    private String userTestResult;

    @Column(nullable = false)
    private String memberTestResult;

    @OneToMany(mappedBy = "post")
    private List<Bookmark> bookmarkList;

    @OneToMany(mappedBy = "post")
    private List<Team> teamList;

    public void setUrl(String frontUrl,String backUrl){
        this.frontUrl = frontUrl;
        this.backUrl = backUrl;
    }

}
