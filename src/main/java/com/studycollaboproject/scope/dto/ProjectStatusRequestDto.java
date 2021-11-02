package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ProjectStatusRequestDto {
    @Schema(description = "프로젝트 상태")
    private String  projectStatus;
}
