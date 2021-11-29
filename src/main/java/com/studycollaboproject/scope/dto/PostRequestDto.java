package com.studycollaboproject.scope.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    @NotBlank
    @Schema(description = "프로젝트 제목")
    private String title;
    @NotBlank
    @Schema(description = "프로젝트 내용")
    private String contents;
    @NotNull
    @Schema(description = "총 인원")
    private Integer totalMember;
    @NotNull
    @Schema(description = "프로젝트 상태")
    private String projectStatus;
    @NotNull
    @Schema(description = "시작 시간")
    private Timestamp startDate;
    @NotNull
    @Schema(description = "종료 시간")
    private Timestamp endDate;
    @NotNull
    @Schema(description = "글쓰기시 프로젝트 기술 스택")
    private List<String> techStackList;
    @Schema(description = "채팅 url")
    private String chatUrl;

}
