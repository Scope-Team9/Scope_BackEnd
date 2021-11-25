package com.studycollaboproject.scope.dto;


import com.studycollaboproject.scope.model.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class MypageResponseDto {
    @Schema(description = "마이페이지에서 사용하는 user 정보")
    private UserResponseDto user;
    @Schema(description = "북마크된 포스트 리스트")
    private List<PostResponseDto> bookmark;

    @Schema(description = "대기상태 포스트 리스트")
    private List<PostResponseDto> ready;
    @Schema(description = "모집중인 포스트 리스트")
    private List<PostResponseDto> recruitment = new ArrayList<>();
    @Schema(description = "진행중인 포스트 리스트")
    private List<PostResponseDto> inProgress = new ArrayList<>();
    @Schema(description = "종료된 포스트 리스트")
    private List<PostResponseDto> end = new ArrayList<>();
    @Schema(description = "사용자의 마이페이지인지 판단")
    private Boolean isMyMypage;

    public MypageResponseDto(List<PostResponseDto> includedList, List<PostResponseDto> readyPostList, List<PostResponseDto> myBookmarkList, UserResponseDto userResponseDto, Boolean equals) {
        this.bookmark = myBookmarkList;
        this.ready = readyPostList;
        for (PostResponseDto responseDto : includedList) {
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
        this.user = userResponseDto;
        this.isMyMypage = equals;
    }
}
