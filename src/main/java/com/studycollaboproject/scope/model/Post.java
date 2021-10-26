package com.studycollaboproject.scope.model;

import com.studycollaboproject.scope.dto.PostReqeustDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends Timestamped{

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
    @Enumerated(value = EnumType.STRING)
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
    private List<TechStack> techStackList;

    @OneToMany(mappedBy = "post")
    private List<Bookmark> bookmarkList;

    @OneToMany(mappedBy = "post")
    private List<Team> teamList;


    public Post(PostReqeustDto postReqeustDto){
        this.title = postReqeustDto.getTitle();
        this.startDate = postReqeustDto.getStartDate();
        this.endDate = postReqeustDto.getEndDate();
        this.summary = postReqeustDto.getSummary();
        this.contents = postReqeustDto.getContents();
        this.totalMember = postReqeustDto.getTotalMember();
        this.recruitmentMember = postReqeustDto.getRecruitmentMember();
        this.projectStatus = ProjectStatus.projectStatusOf(postReqeustDto.getProjectStatus());
        this.bookmarkList = postReqeustDto.getBookmarkList();
        this.teamList = postReqeustDto.getTeamList();
        this.techStackList = postReqeustDto.getTechStackList();
        this.backUrl = postReqeustDto.getBackUrl();
        this.frontUrl = postReqeustDto.getFrontUrl();
    }


    public void update(PostReqeustDto postReqeustDto) {

        this.title = postReqeustDto.getTitle();
        this.startDate = postReqeustDto.getStartDate();
        this.endDate = postReqeustDto.getEndDate();
        this.summary = postReqeustDto.getSummary();
        this.contents = postReqeustDto.getContents();
        this.totalMember = postReqeustDto.getTotalMember();
        this.recruitmentMember = postReqeustDto.getRecruitmentMember();
        this.projectStatus = ProjectStatus.projectStatusOf(postReqeustDto.getProjectStatus());
        this.bookmarkList = postReqeustDto.getBookmarkList();
        this.teamList = postReqeustDto.getTeamList();
        this.techStackList = postReqeustDto.getTechStackList();
        this.backUrl = postReqeustDto.getBackUrl();
        this.frontUrl = postReqeustDto.getFrontUrl();
    }
}
