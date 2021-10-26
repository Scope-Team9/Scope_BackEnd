package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.Bookmark;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.TechStack;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PostReqeustDto {

    private String title;
    private String summary;
    private String contents;
    private List<String> techStack;
    private Integer totalMember;
    private Integer recruitmentMember;
    private String projectStatus;
    private String recommendationAgree;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<Bookmark> bookmarkList;
    private List<Team> teamList;
    private List<TechStack> techStackList;
    private String backUrl;
    private String frontUrl;

}
