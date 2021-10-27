package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.PropensityType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestResultDto {
    private PropensityType userPropensityType;
    private PropensityType memberPropensityType;

    public TestResultDto(PropensityType userPropensityType, PropensityType memberPropensityType) {
        this.userPropensityType = userPropensityType;
        this.memberPropensityType = memberPropensityType;
    }
}
