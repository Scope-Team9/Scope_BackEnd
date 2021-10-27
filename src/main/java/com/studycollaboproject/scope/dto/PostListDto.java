package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class PostListDto {

    private List<Post> boomark;
    private List<Post> recruitment;
    private List<Post> inProgress;
    private List<Post> end;

    public PostListDto(List<Post> bookmark, List<Post> recruitment, List<Post> inProgress, List<Post> end) {
        this.recruitment = recruitment;
        this.end = end;
        this.inProgress = inProgress;
        this.boomark = bookmark;
    }
}
