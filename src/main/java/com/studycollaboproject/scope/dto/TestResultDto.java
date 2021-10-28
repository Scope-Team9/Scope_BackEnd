package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestResultDto {
    @Schema(description = "유저 성향")
    private String userPropensityType;
    @Schema(description = "유저 선호 성향")
    private String memberPropensityType;

    public TestResultDto(String userPropensityType, String memberPropensityType) {
        this.userPropensityType = userPropensityType;
        this.memberPropensityType = memberPropensityType;
    }
}
