package com.studycollaboproject.scope.dto;


import lombok.Getter;

import java.util.List;

@Getter
public class MypageResponseDto {
    private UserResponseDto user;
    private List<PostResponseDto> bookmark;
    private List<PostResponseDto> recruitment;
    private List<PostResponseDto> inProgress;
    private List<PostResponseDto> end;
    private boolean isMyMypage;

    public MypageResponseDto(MypagePostListDto mypagePostListDto, List<PostResponseDto> bookmark, boolean isMyMypage){
        this.user = mypagePostListDto.getUserResponseDto();
        this.bookmark = bookmark;
        this.recruitment = mypagePostListDto.getRecruitment();
        this.inProgress = mypagePostListDto.getInProgress();
        this.end = mypagePostListDto.getEnd();
        this.isMyMypage = isMyMypage;
    }


}
