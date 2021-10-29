package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.LoginReponseDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.SnsInfoDto;
import com.studycollaboproject.scope.dto.UserRepuestDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.*;
import com.studycollaboproject.scope.repository.BookmarkRepository;
import com.studycollaboproject.scope.repository.PostRepository;
import com.studycollaboproject.scope.repository.TechStackRepository;
import com.studycollaboproject.scope.repository.UserRepository;
import com.studycollaboproject.scope.security.JwtTokenProvider;
import com.studycollaboproject.scope.util.TechStackConverter;
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
    private final TechStackConverter techStackConverter;
    private final TechStackRepository techStackRepository;


    public User getUserInfo(String userSnsId) {
        return userRepository.findBySnsId(userSnsId).orElseThrow(
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


    public void saveUser(List<String> techStack, User user) {
        user.addTechStackList(techStackConverter.convertStringToTechStack(techStack,user));
        userRepository.save(user);
    }


    public User loadUserBySnsId(String snsId) {
        return userRepository.findBySnsId(snsId).orElseThrow(
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
        return jwtTokenProvider.createToken(user.getSnsId());

    }


    public boolean emailCheckByUser(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    public boolean nicknameCheckBynickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
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
    public ResponseDto bookmarkCheck(Long postId, String snsId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 포스트를 찾을 수 없습니다.")
        );
        User user = userRepository.findBySnsId(snsId).orElseThrow(
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

    @Transactional
    public ResponseDto updateUserInfo(String snsId, UserRepuestDto userRepuestDto) {
        User user = userRepository.findBySnsId(snsId).orElseThrow(
                ()-> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
        );
        techStackRepository.deleteAllByUser(user);
        user.resetTechStack();
        user.updateUserInfo(userRepuestDto.getEmail(),userRepuestDto.getNickname(),
                techStackConverter.convertStringToTechStack(userRepuestDto.getUserTechStack(),user));

        return new ResponseDto("200","회원 정보가 수정되었습니다.","");




    }
}
