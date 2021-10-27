package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.ProjectStatus;
import com.studycollaboproject.scope.model.Tech;
import com.studycollaboproject.scope.model.TechStack;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "게시글 상세")
public class PostResponseDto {
    @Schema(description = "게시글 ID")
    private Long postId;
    @Schema(description = "게시글 제목")
    private String title;
    @Schema(description = "게시글 한줄요약")
    private String summary;
    @Schema(description = "게시글 내용")
    private String contents;
    @Schema(description = "기술 스택")
    private List<String> techStack;
    @Schema(description = "총 인원")
    private int totalMember;
    @Schema(description = "현재 인원")
    private int recruitmentMember;
    @Schema(description = "게시글 상태")
    private ProjectStatus projectStatus;
    @Schema(description = "생성일")
    private LocalDateTime startDate;
    @Schema(description = "수정일")
    private LocalDateTime endDate;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.summary = post.getSummary();
        this.contents = post.getContents();
        this.techStack = new ArrayList<>();
        List<TechStack> techStackList = post.getTechStackList();
        for (TechStack stack : techStackList) {
            techStack.add(stack.getTech().getTech());
        }
        this.totalMember = post.getTotalMember();
        this.recruitmentMember = post.getRecruitmentMember();
    }
}
