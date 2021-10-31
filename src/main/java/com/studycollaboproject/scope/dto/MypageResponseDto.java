package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.Post;
import lombok.Getter;

import java.util.List;

@Getter
public class MypageResponseDto {
    private UserResponseDto user;
    private List<Post> bookmark;
    private List<Post> recruitment;
    private List<Post> inProgress;
    private List<Post> end;
    private boolean isMyMypage;

    public MypageResponseDto(MypagePostListDto mypagePostListDto, List<Post> bookmark, boolean isMyMypage){
        this.user = mypagePostListDto.getUserResponseDto();
        this.bookmark = bookmark;
        this.recruitment = mypagePostListDto.getRecruitment();
        this.inProgress = mypagePostListDto.getInProgress();
        this.end = mypagePostListDto.getEnd();
        this.isMyMypage = isMyMypage;
    }


}
