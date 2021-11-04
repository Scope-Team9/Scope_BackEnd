package com.studycollaboproject.scope.dto;

import com.studycollaboproject.scope.model.Applicant;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;
import lombok.Getter;

import java.util.List;

@Getter
public class MailDto {

    private String toEmail;
    private String toNickname;
    private String fromNickname;
    private String comment;
    private Long postId;
    private Long toUserId;
    private String postTitle;

    List<User> toUserList;

    public MailDto(Applicant applicant){
        this.comment= applicant.getComment();
        this.toEmail = applicant.getPost().getUser().getEmail();
        this.toNickname = applicant.getPost().getUser().getNickname();
        this.toUserId = applicant.getPost().getUser().getId();
        this.fromNickname = applicant.getUser().getNickname();
        this.postId = applicant.getPost().getId();
    }

    public MailDto(Team team){
        User user = team.getUser();
        Post post = team.getPost();
        this.postTitle = post.getTitle();
        this.postId = post.getId();
        this.toNickname = user.getNickname();
        this.toEmail = user.getEmail();
        this.fromNickname = post.getUser().getNickname();
    }

    public MailDto(List<User> userList, Post post){
        this.postTitle = post.getTitle();
        this.toUserList = userList;
    }
}
