package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostRequestDto {

    @Schema(description = "프로젝트 제목")
    private String title;
    @Schema(description = "프로젝트 요약")
    private String summary;
    @Schema(description = "프로젝트 내용")
    private String contents;
    @Schema(description = "프로젝트 기술 스택")
    private String techStack;
    @Schema(description = "총 인원")
    private Integer totalMember;
    //    private Integer recruitmentMember;
    @Schema(description = "프로젝트 상태")
    private String projectStatus;
    //    private String recommendationAgree;
    @Schema(description = "시작 시간")
    private LocalDate startDate;
    @Schema(description = "종료 시간")
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
