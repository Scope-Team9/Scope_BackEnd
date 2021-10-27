package com.studycollaboproject.scope.service;


import com.studycollaboproject.scope.dto.LoginReponseDto;
import com.studycollaboproject.scope.dto.PostListDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.SignupResponseDto;
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
import java.util.Optional;

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

    public String signup(User user) {
        userRepository.save(user);
        return jwtTokenProvider.createToken(user.getNickname());

    }


    public boolean emailCheckByUser(String email, String username) {
        User user = userRepository.findByNickname(username).orElseThrow(
                () -> new IllegalArgumentException("사용자를 찾을 수 없습니다.")
        );
        return user.getEmail().equals(email);
    }

    public ResponseDto emailCheckByEmail(String email, String sns) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {

            LoginReponseDto loginReponseDto = new LoginReponseDto(jwtTokenProvider.createToken(user.get().getNickname()), user.get().getEmail(), user.get().getNickname());
            return new ResponseDto("200", "로그인이 완료되었습니다", loginReponseDto);
        } else {
            SignupResponseDto signupResponseDto = new SignupResponseDto();
            switch (sns) {
                case "google":
                    signupResponseDto.setEmail("", email, "");
                    break;
                case "kakao":
                    signupResponseDto.setEmail("", "", email);
                    break;
                case "github":
                    signupResponseDto.setEmail(email, "", "");
                    break;
            }
            return new ResponseDto("300", "추가 정보 작성이 필요한 사용자입니다.", signupResponseDto);
        }

    }


}
