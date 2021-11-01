package com.studycollaboproject.scope.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class MypageResponseDto {
    @Schema(description = "마이페이지에서 사용하는 user 정보")
    private UserResponseDto user;
    @Schema(description = "북마크된 포스트 리스트")
    private List<PostResponseDto> bookmark;
    @Schema(description = "모집중인 포스트 리스트")
    private List<PostResponseDto> recruitment;
    @Schema(description = "진행중인 포스트 리스트")
    private List<PostResponseDto> inProgress;
    @Schema(description = "종료된 포스트 리스트")
    private List<PostResponseDto> end;
    @Schema(description = "사용자의 마이페이지인지 판단")
    private boolean isMyMypage;

    public MypageResponseDto(MypagePostListDto mypagePostListDto, List<PostResponseDto> bookmark, boolean isMyMypage){
        this.user = mypagePostListDto.getUser();
        this.bookmark = bookmark;
        this.recruitment = mypagePostListDto.getRecruitment();
        this.inProgress = mypagePostListDto.getInProgress();
        this.end = mypagePostListDto.getEnd();
        this.isMyMypage = isMyMypage;
    }


}
