package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.SignupTestDto;
import com.studycollaboproject.scope.listener.SessionUserCounter;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.UserRepository;
import com.studycollaboproject.scope.security.JwtTokenProvider;
import com.studycollaboproject.scope.service.AssessmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "API Test Controller", description = "API TEST 용 컨트롤러")
public class ApiTestRestController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AssessmentService assessmentService;
    private final SessionUserCounter userCounter;

    @Operation(summary = "TEST용 회원가입")
    @PostMapping("/api-test")
    public Map<String, String> makeApiTestUser(@RequestBody SignupTestDto signupTestDto) {
        String jwtToken = jwtTokenProvider.createToken(signupTestDto.getSnsId());
//        System.out.println("jwtToken = " + jwtToken);
//        System.out.println("jwtToken.split(\".\")[1] = " + jwtToken.split("\\.")[1]);
        User user = new User(signupTestDto, jwtToken.split("\\.")[1]);
        userRepository.save(user);
        Map<String, String> token = new HashMap<>();
        token.put("token", jwtToken);
        return token;
    }

    @Operation(summary = "SNS ID로 JWT 받기")
    @GetMapping("/api-test")
    public Map<String, String> loginApiTestUser(@Parameter(description = "닉네임", in = ParameterIn.QUERY) @RequestParam String snsId) {
        String jwtToken = jwtTokenProvider.createToken(snsId);
        Map<String, String> token = new HashMap<>();
        token.put("token", jwtToken);
        return token;
    }

    @Operation(summary = "팀원평가 저장")
    @PostMapping("/api-test/assessment")
    public ResponseDto saveAssessment(@RequestBody String rater, @RequestBody String memberType, @RequestBody Long count) {
        assessmentService.testAssessmentResult(rater, memberType, count);
        return new ResponseDto("성공적으로 저장", "");
    }

}
