package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.TeamRequestDto;
import com.studycollaboproject.scope.dto.TeamResponseDto;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;
    private final PostService postService;

    @PostMapping("/api/team/{postId}")
    public ResponseDto acceptMember(@PathVariable Long postId, @RequestBody TeamRequestDto requestDto) {
      log.info("POST, /api/team/{}, userId={}, accept={}",postId, requestDto.getUserId(), requestDto.getAccept());

      //로그인 사용자 불러오기
      Post post = postService.loadPostIfOwner(postId, requestDto.getUserId());  //로그인 사용자가 해당 게시글의 생성자 인지 확인
      User applyUser = userService.loadUser(requestDto.getUserId());            //지원자 정보 확인
      teamService.acceptMember(post, applyUser, requestDto.getAccept());        //지원자 승인/거절
      List<TeamResponseDto> responseDto = teamService.getMember(postId);
      return new ResponseDto("200", "", responseDto);
    }

    @GetMapping("/api/team/{postId}")
    public ResponseDto getMember(@PathVariable Long postId){
        List<TeamResponseDto> responseDto = teamService.getMember(postId);
        return new ResponseDto("200", "", responseDto);
    }
}
