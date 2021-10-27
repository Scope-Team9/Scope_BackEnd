package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TestRequestDto {
    @Schema(description = "유저 성향 테스트 결과", example = "[\"F\",\"L\",\"F\",\"V\",\"V\",\"H\",\"P\",\"G\",\"G\"]")
    private List<String> userPropensityType;
    @Schema(description = "유저 선호 성향 테스트 결과", example = "[\"F\",\"L\",\"F\",\"V\",\"V\",\"H\",\"P\",\"G\",\"G\"]")
    private List<String> memberPropensityType;
}
