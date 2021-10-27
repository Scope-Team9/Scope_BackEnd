package com.studycollaboproject.scope.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class TestRequestDto {
    private List<String> userPropensityType;
    private List<String> memberPropensityType;
}
