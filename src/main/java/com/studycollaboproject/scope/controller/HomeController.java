package com.studycollaboproject.scope.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    /**
     * front-end routing 처리
     * @return /index.html
     */
    @GetMapping(value={"/", "/message", "/mypage/**", "/postadd", "/postedit/**", "/addmarkdown" ,
    "/postdetail/**", "/user/kakao/callback","/user/github/callback"})
    public String home () {
        return "index.html";
    }
}