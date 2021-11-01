package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Getter
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
    @Schema(description = "현재 구한 인원")
    private Integer recruitmentMember;
    @Schema(description = "프로젝트 상태")
    private String projectStatus;
    //    private String recommendationAgree;
    @Schema(description = "시작 시간")
    private LocalDate startDate;
    @Schema(description = "종료 시간")
    private LocalDate endDate;
//    private List<Team> teamList;
//    private List<Tech> techStackList;
    @Schema(description = "백엔드 Url")
    private String backUrl;
    @Schema(description = "프론트엔드 Url")
    private String frontUrl;
}
