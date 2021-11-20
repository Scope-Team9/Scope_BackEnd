package com.studycollaboproject.scope.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostListDto {
    @Schema(description = "게시물 리스트")
    private List<PostResponseDto> postList;
    @Schema(description = "페이지 번호")
    private Long nowPage;
    @Schema(description = "총 페이지 수")
    private Long totalPage;

    public PostListDto(List<PostResponseDto> postList, Long nowPage, Long totalPage) {
        this.postList = postList;
        this.nowPage = nowPage;
        this.totalPage = totalPage;
    }
}
