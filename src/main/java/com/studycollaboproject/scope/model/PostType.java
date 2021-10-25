package com.studycollaboproject.scope.model;

import lombok.Getter;

@Getter
public enum PostType {

    POST_TYPE_PRO("프로젝트"),
    POST_TYPE_STU("스터디");

    private final String postType;

    PostType(String postType){
        this.postType = postType;
    }


}
