package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.LoginReponseDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.SnsInfoDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.*;
import com.studycollaboproject.scope.repository.BookmarkRepository;
import com.studycollaboproject.scope.repository.PostRepository;
import com.studycollaboproject.scope.repository.UserRepository;
import com.studycollaboproject.scope.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PostRepository postRepository;


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


    public void setTechStack(List<String> techStack, User user) {
        for (String tech : techStack) {
            switch (tech) {
                case "java":
                    user.addTechStack(new TechStack(Tech.TECH_JAVA, user));
                    break;
                case "JavaScript":
                    user.addTechStack(new TechStack(Tech.TECH_JS, user));
                    break;
                case "Python":
                    user.addTechStack(new TechStack(Tech.TECH_PYTHON, user));
                    break;
                case "Node.js":
                    user.addTechStack(new TechStack(Tech.TECH_NODEJS, user));
                    break;
                case "C++":
                    user.addTechStack(new TechStack(Tech.TECH_CPP, user));
                    break;
                case "Flask":
                    user.addTechStack(new TechStack(Tech.TECH_FLASK, user));
                    break;
                case "Django":
                    user.addTechStack(new TechStack(Tech.TECH_DJANGO, user));
                    break;
                case "Vue.js":
                    user.addTechStack(new TechStack(Tech.TECH_VUE, user));
                    break;
                case "React":
                    user.addTechStack(new TechStack(Tech.TECH_REACT, user));
                    break;
                case "React Native":
                    user.addTechStack(new TechStack(Tech.TECH_REACTNATIVE, user));
                    break;
                case "PHP":
                    user.addTechStack(new TechStack(Tech.TECH_PHP, user));
                    break;
                case "Swift":
                    user.addTechStack(new TechStack(Tech.TECH_SWIFT, user));
                    break;
                case "Kotlin":
                    user.addTechStack(new TechStack(Tech.TECH_KOTLIN, user));
                    break;
                case "TypeScript":
                    user.addTechStack(new TechStack(Tech.TECH_TYPESCRIPT, user));
                    break;
                case "Angular.js":
                    user.addTechStack(new TechStack(Tech.TECH_ANGULAR, user));
                    break;
                case "Spring":
                    user.addTechStack(new TechStack(Tech.TECH_SPRING, user));
                    break;
            }
        }
        userRepository.save(user);
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

    public ResponseDto emailCheckByEmail(SnsInfoDto snsInfoDto) {
        Optional<User> user = userRepository.findByEmail(snsInfoDto.getEmail());
        if (user.isPresent()) {

            LoginReponseDto loginReponseDto = new LoginReponseDto(jwtTokenProvider.createToken(user.get().getNickname()), user.get().getEmail(), user.get().getNickname());
            return new ResponseDto("200", "로그인이 완료되었습니다", loginReponseDto);
        } else {
            return new ResponseDto("300", "추가 정보 작성이 필요한 사용자입니다.", snsInfoDto);
        }

    }

    @Transactional
    public ResponseDto BookmarkCheck(Long postId, String nickname) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 포스트를 찾을 수 없습니다.")
        );
        User user = userRepository.findByNickname(nickname).orElseThrow(
                ()-> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
        );
        if (post.isBookmarkChecked()){
            Bookmark bookmark = bookmarkRepository.findByUserAndPost(user,post);
            bookmarkRepository.delete(bookmark);
            post.setIsBookmarkChecked(false);
            Map<String ,String> isBookmarkChecked = new HashMap<>();
            isBookmarkChecked.put("isBookmarkChecked","false");
            return new ResponseDto("200","북마크 삭제 성공",isBookmarkChecked);
        }else {
            bookmarkRepository.save(new Bookmark(user,post));
            post.setIsBookmarkChecked(true);
            Map<String ,String> isBookmarkChecked = new HashMap<>();
            isBookmarkChecked.put("isBookmarkChecked","true");
            return new ResponseDto("200","북마크 추가 성공",isBookmarkChecked);
        }


    }
}
