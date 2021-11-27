package com.studycollaboproject.scope.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    /**
     * front-end routing 처리
     * @return /index.html
     */
    @GetMapping(value={"/", "/message", "/mypage/:id", "/postadd", "/postedit/:id", "/addmarkdown" ,
    "/postdetail/:id", "/user/kakao/callback","/user/github/callback","/error"})
    public String home () {
        return "index.html";
    }
}