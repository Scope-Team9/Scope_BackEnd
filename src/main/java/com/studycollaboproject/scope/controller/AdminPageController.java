package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.PostResponseDto;
import com.studycollaboproject.scope.dto.UserResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.ForbiddenException;
import com.studycollaboproject.scope.exception.NoAuthException;
import com.studycollaboproject.scope.model.TotalResult;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.TotalResultRepository;
import com.studycollaboproject.scope.repository.UserRepository;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import com.studycollaboproject.scope.service.PostService;
import com.studycollaboproject.scope.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "AdminPage Controller", description = "어드민 페이지")
public class AdminPageController {
    private final PostService postService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final TotalResultRepository totalResultRepository;

    @GetMapping(value = "")
    public String adminSelect(@AuthenticationPrincipal UserDetailsImpl userDetails){

        String snsId = Optional.ofNullable(userDetails).orElseThrow(
                () -> new NoAuthException(ErrorCode.NO_AUTHENTICATION_ERROR)
        ).getSnsId();

        if ("K-1993763306".equals(snsId)) {
            return "adminpage";
        }
        else {
            throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

    }
    @GetMapping(value = "postpage")
    public String getAdminPostList(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String snsId = Optional.ofNullable(userDetails).orElseThrow(
                () -> new NoAuthException(ErrorCode.NO_AUTHENTICATION_ERROR)
        ).getSnsId();

        if ("K-1993763306".equals(snsId)) {
            List<PostResponseDto> postResponseDtos = postService.readPost("", "createdAt", "", "undefined");
            model.addAttribute("postResponseDtos", postResponseDtos);
            return "postPage";
        }
        else {
            throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

    }

    @GetMapping(value = "propensitypage")
    public String getAdminPropensityList(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Map<String,Map<String,Integer>> userPropensityMap = new HashMap<>(Map.of(
                "LVG", new HashMap<>(Map.of(
                        "LVG", 0,
                        "LVP", 0,
                        "LHG", 0,
                        "LHP", 0,
                        "FVG", 0,
                        "FVP", 0,
                        "FHG", 0,
                        "FHP", 0,
                        "RHP", 0
                )),
                "LVP", new HashMap<>(Map.of(
                        "LVG", 0,
                        "LVP", 0,
                        "LHG", 0,
                        "LHP", 0,
                        "FVG", 0,
                        "FVP", 0,
                        "FHG", 0,
                        "FHP", 0,
                        "RHP", 0
                )),
                "LHG", new HashMap<>(Map.of(
                        "LVG", 0,
                        "LVP", 0,
                        "LHG", 0,
                        "LHP", 0,
                        "FVG", 0,
                        "FVP", 0,
                        "FHG", 0,
                        "FHP", 0,
                        "RHP", 0
                )),
                "LHP", new HashMap<>(Map.of(
                        "LVG", 0,
                        "LVP", 0,
                        "LHG", 0,
                        "LHP", 0,
                        "FVG", 0,
                        "FVP", 0,
                        "FHG", 0,
                        "FHP", 0,
                        "RHP", 0
                )),
                "FVG", new HashMap<>(Map.of(
                        "LVG", 0,
                        "LVP", 0,
                        "LHG", 0,
                        "LHP", 0,
                        "FVG", 0,
                        "FVP", 0,
                        "FHG", 0,
                        "FHP", 0,
                        "RHP", 0
                )),
                "FVP",new HashMap<>(Map.of(
                        "LVG", 0,
                        "LVP", 0,
                        "LHG", 0,
                        "LHP", 0,
                        "FVG", 0,
                        "FVP", 0,
                        "FHG", 0,
                        "FHP", 0,
                        "RHP", 0
                )),
                "FHG", new HashMap<>(Map.of(
                        "LVG", 0,
                        "LVP", 0,
                        "LHG", 0,
                        "LHP", 0,
                        "FVG", 0,
                        "FVP", 0,
                        "FHG", 0,
                        "FHP", 0,
                        "RHP", 0
                )),
                "FHP", new HashMap<>(Map.of(
                        "LVG", 0,
                        "LVP", 0,
                        "LHG", 0,
                        "LHP", 0,
                        "FVG", 0,
                        "FVP", 0,
                        "FHG", 0,
                        "FHP", 0,
                        "RHP", 0
                )),
                "RHP",new HashMap<>(Map.of(
                        "LVG", 0,
                        "LVP", 0,
                        "LHG", 0,
                        "LHP", 0,
                        "FVG", 0,
                        "FVP", 0,
                        "FHG", 0,
                        "FHP", 0,
                        "RHP", 0
                )),
                "unknown",new HashMap<>(Map.of(
                "LVG", 0,
                "LVP", 0,
                "LHG", 0,
                "LHP", 0,
                "FVG", 0,
                "FVP", 0,
                "FHG", 0,
                "FHP", 0,
                "RHP", 0
                ))
        ));

//        List<UserResponseDto> userResponseDtos = userService.adminUserPropensityType();
        List<TotalResult> totalResult = totalResultRepository.findAll();
        for ( TotalResult result : totalResult) {
//            String userPropensityType = userResponseDto.getUserPropensityType();
//            String memberPropenstiyType = userResponseDto.getMemberPropensityType();
            String userPropensityType = result.getUserType();
            String memberPropenstiyType = result.getMemberType();
            int num = result.getResult().intValue();
            userPropensityMap.get(userPropensityType).replace(memberPropenstiyType,num);
        }
        System.out.println(userPropensityMap);
        String snsId = Optional.ofNullable(userDetails).orElseThrow(
                () -> new NoAuthException(ErrorCode.NO_AUTHENTICATION_ERROR)
        ).getSnsId();
        if ("K-1993763306".equals(snsId)) {

            model.addAttribute("map",userPropensityMap);
            return "propensityPage";
        }
        else {
            throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }


    }

    @GetMapping(value = "usermanagementpage")
    public String getAdminUserList(Model model, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        String snsId = Optional.ofNullable(userDetails).orElseThrow(
                () -> new NoAuthException(ErrorCode.NO_AUTHENTICATION_ERROR)
        ).getSnsId();

        List<UserResponseDto> userResponseDtos = userService.adminUserPropensityType();

        if ("K-1993763306".equals(snsId)) {

            model.addAttribute("userResponseDtos", userResponseDtos);
            return "userManagementPage";
        }
        else {
            throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

    }
    @GetMapping(value = "reportpage")
    public String getReportList(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        String snsId = Optional.ofNullable(userDetails).orElseThrow(
                () -> new NoAuthException(ErrorCode.NO_AUTHENTICATION_ERROR)
        ).getSnsId();

        if ("K-1993763306".equals(snsId)) {
            return "reportPage";
        }
        else {
            throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

    }

    @ResponseBody
    @PostMapping(value = "admindelete")
    public List<Long> deletePost(@RequestBody List<Long> postIds,@AuthenticationPrincipal UserDetailsImpl userDetails){

        System.out.println(postIds);

    String snsId = Optional.ofNullable(userDetails).orElseThrow(
                () -> new NoAuthException(ErrorCode.NO_AUTHENTICATION_ERROR)
        ).getSnsId();

        if ("K-1993763306".equals(snsId)) {
            for (Long postId : postIds) {
                postService.adminDeletePost(postId);
            }
            return postIds;
        }
        else {
            throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
    }

    @ResponseBody
    @PostMapping(value = "adminuserdelete")
    public List<Long> deleteuser(@RequestBody List<Long> userIds, @AuthenticationPrincipal UserDetailsImpl userDetails){
        System.out.println(userIds);

        String snsId = Optional.ofNullable(userDetails).orElseThrow(
                () -> new NoAuthException(ErrorCode.NO_AUTHENTICATION_ERROR)
        ).getSnsId();

        if ("K-1993763306".equals(snsId)) {
            for (Long userId: userIds){
                User user = userRepository.findById(userId).
                        orElseThrow(() ->new NoAuthException(ErrorCode.NO_USER_ERROR));
                System.out.println(user.getNickname());
                userService.deleteUser(user);
            }
            return userIds;
        }
        else {
            throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

    }


}

