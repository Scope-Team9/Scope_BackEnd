package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.MessageRequestDto;
import com.studycollaboproject.scope.dto.MessageResponseDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.NoAuthException;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.MessageService;
import com.studycollaboproject.scope.webSocket.AlertService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageController {
    private final MessageService messageService;
    private final AlertService alertService;
    @PostMapping("/api/message")
    public ResponseEntity<Object> writeMessage(@RequestBody MessageRequestDto messageRequestDto,
                                               @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails, HttpSession session)
    {
        log.info("POST, [{}], /api/post, requestDto={}", MDC.get("UUID"), messageRequestDto.toString());
        // [예외처리] 로그인 정보가 없을 때
        String snsId = Optional.ofNullable(userDetails).orElseThrow(
                () -> new NoAuthException(ErrorCode.NO_AUTHENTICATION_ERROR)
        ).getSnsId();

        // 쪽지 보냈을 때 메세지 저장
        MessageResponseDto responseDto = messageService.sentMessage(messageRequestDto, snsId);
        alertService.sendMsg(messageRequestDto);
        // responseDto에 저장 후 응답
        return new ResponseEntity<>(
                new ResponseDto("메세지가 성공적으로 발송되었습니다.", responseDto),
                HttpStatus.CREATED
        );
    }

}

