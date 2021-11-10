package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostDetailDto {
    @Schema(description = "프로젝트 상세")
    private PostResponseDto post;
    @Schema(description = "프로젝트 맴버")
    private List<MemberListResponseDto> members;
    @Schema(description = "유저 상태")
    private String userStatus;


    public PostDetailDto(PostResponseDto postDetail, List<MemberListResponseDto> members, String userStatus) {
        this.post = postDetail;
        this.members = members;
        this.userStatus = userStatus;
    }
}
