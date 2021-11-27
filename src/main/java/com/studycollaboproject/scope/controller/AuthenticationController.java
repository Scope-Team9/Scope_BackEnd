package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuthenticationController {

    private final MailService mailService;

    @Operation(summary = "이메일 인증 코드 확인")
    @GetMapping("api/user/auth/email/{userId}")
    public String recEmailCode(@Parameter(description = "인증 코드", in = ParameterIn.QUERY) @RequestParam String code,
                               @Parameter(description = "프로젝트 ID", in = ParameterIn.PATH) @PathVariable Long userId, Model model) {
        log.info("[{}], 이메일 인증 코드 확인, GET, api/user/email/auth/{}, code={}", MDC.get("UUID"), userId, code);
        System.out.println("여기까지는 옴");
        String msg = mailService.emailAuthCodeCheck(code, userId);
        model.addAttribute("msg",msg);
        return "responsePage";
    }
}
