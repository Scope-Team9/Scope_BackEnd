package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.ProjectStatus;
import com.studycollaboproject.scope.model.TechStack;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Schema(description = "프로젝트 상세")
public class PostResponseDto {
    @Schema(description = "프로젝트 ID")
    private Long postId;
    @Schema(description = "프로젝트 제목")
    private String title;
    @Schema(description = "프로젝트 한줄요약")
    private String summary;
    @Schema(description = "프로젝트 내용")
    private String contents;
    @Schema(description = "기술 스택")
    private List<String> techStack;
    @Schema(description = "총 인원")
    private int totalMember;
    @Schema(description = "현재 인원")
    private int recruitmentMember;
    @Schema(description = "프로젝트 상태")
    private ProjectStatus projectStatus;
    @Schema(description = "생성일")
    private LocalDate startDate;
    @Schema(description = "수정일")
    private LocalDate endDate;
    @Schema(description = "북마크 체크여부")
    private boolean bookmarkChecked;

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
        this.endDate = post.getEndDate();
        this.startDate = post.getStartDate();
        this.projectStatus = post.getProjectStatus();
    }

    public PostResponseDto(Post post,boolean bookmarkChecked) {
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
        this.bookmarkChecked = bookmarkChecked;
        this.endDate = post.getEndDate();
        this.startDate = post.getStartDate();
        this.projectStatus = post.getProjectStatus();
    }
}
