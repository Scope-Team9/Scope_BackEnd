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
    @Schema(description = "게시글 작성자인지 여부")
    private boolean isTeamStarter;
    @Schema(description = "북마크 체크 여부")
    private boolean isBookmarkChecked;

    public PostDetailDto(PostResponseDto postDetail, List<MemberListResponseDto> members,boolean isTeamStarter, boolean isBookmarkChecked) {
        this.post = postDetail;
        this.members = members;
        this.isTeamStarter = isTeamStarter;
        this.isBookmarkChecked = isBookmarkChecked;
    }
}
