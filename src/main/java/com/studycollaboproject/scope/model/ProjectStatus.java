package com.studycollaboproject.scope.model;

import lombok.Getter;

@Getter
public enum ProjectStatus {

    PROJECT_STATUS_INPROGRESS("진행중"),
    PROJECT_STATUS_END("종료"),
    PROJECT_STATUS_RECRUITMENT("모집중");

    private final String projectStatus;

    ProjectStatus(String projectStatus){
        this.projectStatus = projectStatus;
    }

}
