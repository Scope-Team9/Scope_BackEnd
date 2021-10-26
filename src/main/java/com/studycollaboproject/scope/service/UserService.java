package com.studycollaboproject.scope.service;


import com.studycollaboproject.scope.dto.PostListDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.*;
import com.studycollaboproject.scope.repository.BookmarkRepository;
import com.studycollaboproject.scope.repository.TeamRepository;
import com.studycollaboproject.scope.repository.UserRepository;
import com.studycollaboproject.scope.security.JwtTokenProvider;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final TeamRepository teamRepository;
    private final JwtTokenProvider jwtTokenProvider;


    public User getUserInfo(String userNickname) {
        return userRepository.findByNickname(userNickname).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

    }

    public List<Post> getBookmarkList(User user) {
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByUser(user);
        List<Post> postList = new ArrayList<>();
        for (Bookmark bookmark : bookmarkList) {
            postList.add(bookmark.getPost());
        }
        return postList;
    }


    public PostListDto getPostList(User user, List<Post> bookmarkList) {
        List<Team> teamList = teamRepository.findAllByUser(user);

        List<Post> inProgressList = new ArrayList<>();
        List<Post> endList = new ArrayList<>();
        List<Post> recruitmentList = new ArrayList<>();

        for (Team team : teamList) {
            switch (team.getPost().getProjectStatus().getProjectStatus()) {
                case "진행중":
                    inProgressList.add(team.getPost());
                    break;
                case "종료":
                    endList.add(team.getPost());
                    break;
                case "모집중":
                    recruitmentList.add(team.getPost());
                    break;
            }
        }
        return new PostListDto(bookmarkList, recruitmentList, inProgressList, endList);
    }

    public User loadUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_USER_ERROR)
        );
    }

    public User loadUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_USER_ERROR)
        );
    }

    public String  signup(User user) {
        userRepository.save(user);
        forceLogin(user);
        return jwtTokenProvider.createToken(user.getNickname());

    }

    private void forceLogin(User user) {
        // 4. 강제 로그인 처리
        UserDetails userDetails = new UserDetailsImpl(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public ResponseDto emailCheck(String email, String username) {
        userRepository.findByNickname(username).orElseThrow(
                ()-> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        if (userRepository.findByEmail(email).isPresent()){
                return new ResponseDto("400","중복된 이메일이 존재합니다.","");
        }else{
            return new ResponseDto("200","사용가능한 메일입니다.","");


        }
    }
}
