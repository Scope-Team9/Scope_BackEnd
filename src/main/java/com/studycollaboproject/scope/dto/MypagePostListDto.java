package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MypagePostListDto {

    @Schema(description = "북마크 프로젝트 리스트")
    private List<PostResponseDto> bookmark = new ArrayList<>();
    @Schema(description = "모집 프로젝트 리스트")
    private List<PostResponseDto> recruitment = new ArrayList<>();
    @Schema(description = "진행중 프로젝트 리스트")
    private List<PostResponseDto> inProgress = new ArrayList<>();
    @Schema(description = "완료된 프로젝트 리스트")
    private List<PostResponseDto> end = new ArrayList<>();
    @Schema(description = "조회하고자 하는 유저")
    private UserResponseDto user;

    public MypagePostListDto(UserResponseDto userResponseDto, List<PostResponseDto> recruitment, List<PostResponseDto> inProgress, List<PostResponseDto> end) {
        this.recruitment = recruitment;
        this.end = end;
        this.inProgress = inProgress;
        this.user = userResponseDto;


    }

    public MypagePostListDto(List<PostResponseDto> includePostList, List<PostResponseDto> bookmarkList, UserResponseDto user) {
        this.bookmark = bookmarkList;
        for (PostResponseDto responseDto : includePostList) {
            switch (responseDto.getProjectStatus()) {
                case "모집중":
                    this.recruitment.add(responseDto);
                    break;
                case "진행중":
                    this.inProgress.add(responseDto);
                    break;
                case "종료":
                    this.end.add(responseDto);
                    break;
                default:
                    break;
            }
        }
        this.user = user;
    }
}
