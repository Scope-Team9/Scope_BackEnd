package com.studycollaboproject.scope.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@ToString
public class PostRequestDto {

    private String title;
    private String summary;
    private String contents;
    private String techStack;
    private Integer totalMember;
//    private Integer recruitmentMember;
    private String projectStatus;
    //    private String recommendationAgree;
    private LocalDate startDate;
    private LocalDate endDate;
    //    private List<Bookmark> bookmarkList;
//    private List<Team> teamList;
//    private List<Tech> techStackList;
//    private String backUrl;
//    private String frontUrl;

    public PostRequestDto(String title,
                          String summary,
                          String contents,
                          String techStack,
                          Integer totalMember,
                          String projectStatus,
                          String startDate,
                          String endDate
    ) {
//        List<String> techStackToList = Arrays.asList(techStack.split(";"));
        this.title = title;
        this.summary = summary;
        this.contents = contents;
        this.techStack = techStack;
        this.totalMember = totalMember;
        this.projectStatus = projectStatus;
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
    }
}
