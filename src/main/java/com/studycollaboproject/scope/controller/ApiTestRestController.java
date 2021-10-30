package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.UserRepository;
import com.studycollaboproject.scope.security.JwtTokenProvider;
import com.studycollaboproject.scope.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
public class ApiTestRestController {

    UserRepository userRepository;
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/api-test")
    public String  makeApiTestUser(@RequestBody String snsId,
                                @RequestBody String userPropensityType,@RequestBody String memberPropensityType){
        User user = new User(snsId,userPropensityType,memberPropensityType);
        userRepository.save(user);

        return jwtTokenProvider.createToken(snsId);
    }
}
