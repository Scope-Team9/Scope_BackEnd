package com.studycollaboproject.scope.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PropensityType {
    LEADING_VERTICAL_GOAL("LVG"),
    LEADING_VERTICAL_PROCESS("LVP"),
    LEADING_HORIZONTAL_GOAL("LHG"),
    LEADING_HORIZONTAL_PROCESS("LHP"),
    FOLLOWING_VERTICAL_GOAL("FVG"),
    FOLLOWING_VERTICAL_PROCESS("FVP"),
    FOLLOWING_HORIZONTAL_GOAL("FHG"),
    FOLLOWING_HORIZONTAL_PROCESS("FHP");

    private String propensityType;
}
