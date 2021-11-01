package com.studycollaboproject.scope.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    @Schema(description = "프로젝트 제목")
    private String title;
    @Schema(description = "프로젝트 요약")
    private String summary;
    @Schema(description = "프로젝트 내용")
    private String contents;
    @Schema(description = "총 인원")
    private Integer totalMember;
    @Schema(description = "프로젝트 상태")
    private String projectStatus;
    //    private String recommendationAgree;
    @Schema(description = "시작 시간")
    private LocalDate startDate;
    @Schema(description = "종료 시간")
    private LocalDate endDate;
//    private List<Team> teamList;
    @Schema(description = "글쓰기시 프로젝트 기술 스택")
    private List<String> techStackList;
}
