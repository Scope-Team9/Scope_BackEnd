package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class MypagePostListDto {

    @Schema(description = "북마크 프로젝트 리스트")
    private List<PostResponseDto> boomark;
    @Schema(description = "모집 프로젝트 리스트")
    private List<PostResponseDto> recruitment;
    @Schema(description = "진행중 프로젝트 리스트")
    private List<PostResponseDto> inProgress;
    @Schema(description = "완료된 프로젝트 리스트")
    private List<PostResponseDto> end;
    @Schema(description = "조회하고자 하는 유저")
    private UserResponseDto userResponseDto;

    public MypagePostListDto(UserResponseDto user,  List<PostResponseDto> recruitment, List<PostResponseDto> inProgress,List<PostResponseDto> end) {
        this.recruitment = recruitment;
        this.end = end;
        this.inProgress = inProgress;
        this.userResponseDto = user;


    }
}
