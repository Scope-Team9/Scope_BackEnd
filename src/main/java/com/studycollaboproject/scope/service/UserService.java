package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.LoginReponseDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.SnsInfoDto;
import com.studycollaboproject.scope.dto.UserRequestDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Bookmark;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.BookmarkRepository;
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
    private final TechStackConverter techStackConverter;
    private final TechStackRepository techStackRepository;
    private final PostService postService;

    //user가 북마크한 post 리스트 반환
    public List<Post> getBookmarkList(User user) {
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByUser(user);
        List<Post> postList = new ArrayList<>();
        for (Bookmark bookmark : bookmarkList) {
            postList.add(bookmark.getPost());
        }
        return postList;
    }

    //기술스택 리스트와 유저 정보를 같이 DB에 저장
    public void saveUser(List<String> techStack, User user) {
        user.addTechStackList(techStackConverter.convertStringToTechStack(techStack,user));
        userRepository.save(user);
    }

    // sns id로 user 정보 반환
    public User loadUserBySnsId(String snsId) {
        return userRepository.findBySnsId(snsId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_USER_ERROR));
    }

    //user id로 정보 반환
    public User loadUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_USER_ERROR));
    }

    //user sns id로 JWT토큰 생성 후 반환
    public String createToken(User user) {
        return jwtTokenProvider.createToken(user.getSnsId());
    }

    //email 중복 체크
    public boolean emailCheckByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    //닉네임 중복 체크
    public boolean nicknameCheckByNickname(String nickname) {
        return userRepository.findByNickname(nickname).isPresent();
    }

    //sns 로그인 시 기존 회원 여부 판단
    public ResponseDto SignupEmailCheck(String snsId, Long id) {
        Optional<User> user = userRepository.findBySnsId(snsId);
        if (user.isPresent()) {

            LoginReponseDto loginReponseDto = new LoginReponseDto(jwtTokenProvider.createToken(user.get().getSnsId()), user.get().getEmail(), user.get().getNickname());
            return new ResponseDto("200", "로그인이 완료되었습니다", loginReponseDto);
        } else {
            return new ResponseDto("300", "추가 정보 작성이 필요한 사용자입니다.",new SnsInfoDto(snsId,id));
        }
    }

    //북마크 체크여부 판단
    @Transactional
    public ResponseDto bookmarkCheck(Long postId, String snsId) {
        Post post = postService.loadPostByPostId(postId);
        User user = loadUserBySnsId(snsId);
        Map<String ,String> isBookmarkChecked = new HashMap<>();

        if (post.isBookmarkChecked()){
            Bookmark bookmark = bookmarkRepository.findByUserAndPost(user,post);
            bookmarkRepository.delete(bookmark);
            post.setIsBookmarkChecked(false);

            isBookmarkChecked.put("isBookmarkChecked","false");
            return new ResponseDto("200","북마크 삭제 성공",isBookmarkChecked);
        }else {
            bookmarkRepository.save(new Bookmark(user,post));
            post.setIsBookmarkChecked(true);

            isBookmarkChecked.put("isBookmarkChecked","true");
            return new ResponseDto("200","북마크 추가 성공",isBookmarkChecked);
        }
    }

    //email, nickname, 기술스택 수정
    @Transactional
    public ResponseDto updateUserInfo(String snsId, UserRequestDto userRequestDto) {

        String nickname = userRequestDto.getNickname();
        String email = userRequestDto.getEmail();
        User user = loadUserBySnsId(snsId);

        techStackRepository.deleteAllByUser(user);
        user.resetTechStack();

        if (nicknameCheckByNickname(nickname)){
            return new ResponseDto("400", "중복된 닉네임이 존재합니다.", "");
        }else if(emailCheckByEmail(email)){
            return new ResponseDto("400", "중복된 이메일이 존재합니다.", "");
        }
        user.updateUserInfo(email,nickname,
                techStackConverter.convertStringToTechStack(userRequestDto.getUserTechStack(),user));

        return new ResponseDto("200","회원 정보가 수정되었습니다.","");
    }

    // 회원 소개 수정
    @Transactional
    public ResponseDto updateUserDesc(String snsId, String userDesc) {
        User user = loadUserBySnsId(snsId);
        user.updateUserInfo(userDesc);
        return new ResponseDto("200","회원 정보가 수정되었습니다.","");
    }
}
