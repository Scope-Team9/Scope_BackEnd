package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostDetailDto {
    @Schema(description = "게시글 상세")
    private PostResponseDto post;
    @Schema(description = "게시글 맴버")
    private List<MemberListResponseDto> members;

    public PostDetailDto(PostResponseDto postDetail, List<MemberListResponseDto> members) {
        this.post = postDetail;
        this.members = members;
    }
}
