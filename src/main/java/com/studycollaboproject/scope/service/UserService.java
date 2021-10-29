package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.LoginReponseDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.SnsInfoDto;
import com.studycollaboproject.scope.dto.UserRequestDto;
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


    public boolean emailCheckByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    public boolean nicknameCheckByNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    public ResponseDto SignupEmailCheck(String email, Long id) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {

            LoginReponseDto loginReponseDto = new LoginReponseDto(jwtTokenProvider.createToken(user.get().getSnsId()), user.get().getEmail(), user.get().getNickname());
            return new ResponseDto("200", "로그인이 완료되었습니다", loginReponseDto);
        } else {
            return new ResponseDto("300", "추가 정보 작성이 필요한 사용자입니다.",new SnsInfoDto(email,id));
        }
    }

    @Transactional
    public ResponseDto bookmarkCheck(Long postId, String snsId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new IllegalArgumentException("해당 포스트를 찾을 수 없습니다.")
        );
        User user = loadUserBySnsId(snsId);
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
    public ResponseDto updateUserInfo(String snsId, UserRequestDto userRequestDto) {
        User user = loadUserBySnsId(snsId);
        techStackRepository.deleteAllByUser(user);
        user.resetTechStack();
        String nickname = userRequestDto.getNickname();
        String email = userRequestDto.getEmail();
        if (nicknameCheckByNickname(nickname)){
            return new ResponseDto("400", "중복된 닉네임이 존재합니다.", "");
        }else if(emailCheckByEmail(email)){
            return new ResponseDto("400", "중복된 이메일이 존재합니다.", "");
        }
        user.updateUserInfo(email,nickname,
                techStackConverter.convertStringToTechStack(userRequestDto.getUserTechStack(),user));

        return new ResponseDto("200","회원 정보가 수정되었습니다.","");
    }

    @Transactional
    public ResponseDto updateUserDesc(String snsId, String userDesc) {
        User user = userRepository.findBySnsId(snsId).orElseThrow(
                ()-> new IllegalArgumentException("해당 사용자를 찾을 수 없습니다.")
        );
        user.updateUserInfo(userDesc);
        return new ResponseDto("200","회원 정보가 수정되었습니다.","");
    }
}
