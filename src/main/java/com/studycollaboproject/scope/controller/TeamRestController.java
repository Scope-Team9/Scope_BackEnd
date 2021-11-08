package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.MailDto;
import com.studycollaboproject.scope.dto.MemberListResponseDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.TeamRequestDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.MailService;
import com.studycollaboproject.scope.service.TeamService;
import com.studycollaboproject.scope.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Team Controller", description = "팀원 조회 및 팀원 승인")
public class TeamRestController {

    private final TeamService teamService;
    private final UserService userService;
    private final PostService postService;
    private final MailService mailService;

    @Operation(summary = "팀원 승인/거절")
    @PostMapping("/api/team/{postId}")
    public ResponseEntity<Object> acceptMember(@Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId,
                                               @RequestBody TeamRequestDto requestDto,
                                               @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) throws MessagingException {
        log.info("POST, [{}], /api/team/{}, userId={}, accept={}", MDC.get("UUID"), postId, requestDto.getUserId(), requestDto.isAccept());
        // [예외처리] 로그인 정보가 없을 때
        if (userDetails == null) {
            throw new RestApiException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
        //로그인 사용자 정보 불러오기
        User user = userService.loadUserBySnsId(userDetails.getSnsId());
        //로그인 사용자가 해당 프로젝트의 생성자 인지 확인
        Post post = postService.loadPostIfOwner(postId, user);
        //지원자 정보 확인
        User applyUser = userService.loadUserByUserId(requestDto.getUserId());
        //지원자 승인/거절
        Team team = teamService.acceptMember(post, applyUser, requestDto.isAccept());
        //지원자 승인시 승인된 지원자에게 알림 메일 발송
        if (team != null) {
            mailService.acceptTeamMailBuilder(new MailDto(team));
        }
        //지원지 목록 출력
        List<MemberListResponseDto> responseDto = teamService.getMember(postId);
        return new ResponseEntity<>(
                new ResponseDto("신청 상태가 변경되었습니다.", responseDto),
                HttpStatus.OK
        );
    }

    @Operation(summary = "팀원 조회")
    @GetMapping("/api/team/{postId}")
    public ResponseEntity<Object> getMember(@Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long postId) {
        log.info("GET, [{}], /api/team/{}", MDC.get("UUID"), postId);
        List<MemberListResponseDto> responseDto = teamService.getMember(postId);
        return new ResponseEntity<>(
                new ResponseDto("팀원을 조회하였습니다.", responseDto),
                HttpStatus.OK
        );
    }

    @Operation(summary = "팀원 강퇴")
    @DeleteMapping("/api/team/resignation")
    public ResponseEntity<Object> memberResignation(@RequestBody Long memberId, @RequestBody Long postId,
                                                    @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("DELETE, [{}], /api/team/resignation", MDC.get("UUID"));
        Post post = postService.loadPostByPostId(postId);
        User user = userDetails.getUser();

        if (post.getUser().equals(user)) {
            User member = userService.loadUserByUserId(memberId);
            teamService.memberResignation(member, post);
            return new ResponseEntity<>(
                    new ResponseDto("팀에서 팀원을 삭제했습니다.", ""),
                    HttpStatus.OK
            );
            // [예외처리] 팀원 강퇴를 요청한 사용자가 게시물 작성자가 아닐 때
        } else throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);
    }

    @Operation(summary = "팀 탈퇴")
    @DeleteMapping("/api/team/secession")
    public ResponseEntity<Object> memberSecession(@RequestBody Long postId,
                                                  @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        log.info("DELETE, [{}], /api/team/secession", MDC.get("UUID"));
        Post post = postService.loadPostByPostId(postId);
        User user = userDetails.getUser();
        teamService.memberSecession(user, post);
        return new ResponseEntity<>(
                new ResponseDto("팀에서 나왔습니다.", ""),
                HttpStatus.OK
        );
    }

}
