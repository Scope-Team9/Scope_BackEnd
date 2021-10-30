package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.UserRepository;
import com.studycollaboproject.scope.security.JwtTokenProvider;
import com.studycollaboproject.scope.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
public class ApiTestRestController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/api-test")
    public Map<String,String>  makeApiTestUser(@ModelAttribute("snsId") String snsId,
                                   @ModelAttribute("userPropensityType") String userPropensityType,@ModelAttribute("memberPropensityType") String memberPropensityType,
                                   @ModelAttribute("nickname") String nickname, @ModelAttribute("email") String email){
        User user = new User(snsId,userPropensityType,memberPropensityType,nickname,email);
        userRepository.save(user);
        String jwtToken =jwtTokenProvider.createToken(snsId);
        Map<String ,String> token = new HashMap<>();
        token.put("token",jwtToken);
        return token;
    }
}
