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


    public static ProjectStatus projectStatusOf(String status) {
        for (ProjectStatus projectStatus : ProjectStatus.values()) {
            if (status.equals(projectStatus.getProjectStatus()))
            {
                return projectStatus;
            }
        }
        throw new IllegalArgumentException("올바른 상태가 아닙니다.");
    }

}
