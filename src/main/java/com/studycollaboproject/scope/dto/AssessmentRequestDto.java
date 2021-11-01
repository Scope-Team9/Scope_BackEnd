package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class AssessmentRequestDto {
    @Schema(description = "평가 아이디")
    private List<Long> userIds;
}
