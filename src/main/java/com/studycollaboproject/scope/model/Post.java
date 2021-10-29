package com.studycollaboproject.scope.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.studycollaboproject.scope.dto.PostRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Column(nullable = false)
    private boolean isBookmarkChecked;

    @Column(nullable = true)
    private String frontUrl;

    @Column(nullable = true)
    private String backUrl;

    @Column(nullable = true)
    private String userTestResult;

    @Column(nullable = true)
    private String memberTestResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<TechStack> techStackList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<Team> teamList = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @JsonIgnore
    private List<Applicant> applicantList = new ArrayList<>();

    public Post(PostRequestDto postRequestDto){
        this.title = postRequestDto.getTitle();
        this.startDate = postRequestDto.getStartDate();
        this.endDate = postRequestDto.getEndDate();
        this.summary = postRequestDto.getSummary();
        this.contents = postRequestDto.getContents();
        this.totalMember = postRequestDto.getTotalMember();
//        this.recruitmentMember = postRequestDto.getRecruitmentMember();
        this.projectStatus = ProjectStatus.projectStatusOf(postRequestDto.getProjectStatus());
//        this.bookmarkList = postRequestDto.getBookmarkList();
//        this.teamList = postRequestDto.getTeamList();
//        this.techStackList = postRequestDto.getTechStackList();
//        this.backUrl = postRequestDto.getBackUrl();
//        this.frontUrl = postRequestDto.getFrontUrl();
        this.isBookmarkChecked = false;
    }

    public void update(PostRequestDto postRequestDto) {

        this.title = postRequestDto.getTitle();
        this.startDate = postRequestDto.getStartDate();
        this.endDate = postRequestDto.getEndDate();
        this.summary = postRequestDto.getSummary();
        this.contents = postRequestDto.getContents();
        this.totalMember = postRequestDto.getTotalMember();
//        this.recruitmentMember = postRequestDto.getRecruitmentMember();
        this.projectStatus = ProjectStatus.projectStatusOf(postRequestDto.getProjectStatus());
//        this.bookmarkList = postRequestDto.getBookmarkList();
//        this.teamList = postRequestDto.getTeamList();
//        this.techStackList = postRequestDto.getTechStackList();
//        this.backUrl = postRequestDto.getBackUrl();
//        this.frontUrl = postRequestDto.getFrontUrl();
    }

    public void setUrl(String frontUrl, String backUrl) {
        this.frontUrl = frontUrl;
        this.backUrl = backUrl;
    }

    public void setIsBookmarkChecked(boolean isBookmarkChecked){
        this.isBookmarkChecked = isBookmarkChecked;
    }

    public void updateStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public void updateMember() {
        this.recruitmentMember += 1;
    }

    public void updateTechStack(List<TechStack> techStackList) {
        this.techStackList = techStackList;
    }
}
