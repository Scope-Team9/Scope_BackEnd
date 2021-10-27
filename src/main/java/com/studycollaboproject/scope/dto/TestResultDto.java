package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.PropensityType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestResultDto {
    @Schema(description = "유저 성향")
    private PropensityType userPropensityType;
    @Schema(description = "유저 선호 성향")
    private PropensityType memberPropensityType;

    public TestResultDto(PropensityType userPropensityType, PropensityType memberPropensityType) {
        this.userPropensityType = userPropensityType;
        this.memberPropensityType = memberPropensityType;
    }
}
