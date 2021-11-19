package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.*;
import com.studycollaboproject.scope.exception.BadRequestException;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.model.Bookmark;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.TechStack;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.*;
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
    private final PostRepository postRepository;
    private final PostService postService;
    private final ApplicantRepository applicantRepository;
    private final TeamRepository teamRepository;

    //기술스택 리스트와 유저 정보를 같이 DB에 저장
    public UserResponseDto saveUser(List<String> techStack, User user, String token) {
        Set<String> techList = new HashSet<>(techStack);
        List<TechStack> techStackList = techStackConverter.convertStringToTechStack(new ArrayList<>(techList), user, null);
        user.addTechStackListAndToken(techStackList, token);
        User savedUser = userRepository.save(user);
        techStackRepository.saveAll(techStackList);
        return new UserResponseDto(savedUser, techStackConverter.convertTechStackToString(user.getTechStackList()));
    }

    // sns id로 user 정보 반환
    public User loadUserBySnsId(String snsId) {
        return userRepository.findBySnsId(snsId).orElseThrow(
                () -> new BadRequestException(ErrorCode.NO_USER_ERROR));
    }

    //user id로 정보 반환
    public User loadUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new BadRequestException(ErrorCode.NO_USER_ERROR));
    }

    public User loadUnknownUser() {
        return userRepository.findByUserPropensityType("unknown");
    }

    //user sns id로 JWT토큰 생성 후 반환
    public String createToken(User user) {
        return jwtTokenProvider.createToken(user.getSnsId());
    }

    //email 중복 체크
    public boolean emailCheckByEmail(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            throw new BadRequestException(ErrorCode.ALREADY_EMAIL_ERROR);
        });
        return true;
    }

    //닉네임 중복 체크
    public boolean nicknameCheckByNickname(String nickname) {
        userRepository.findByNickname(nickname).ifPresent(user -> {
            throw new BadRequestException(ErrorCode.ALREADY_NICKNAME_ERROR);
        });
        return true;
    }

    //sns 로그인 시 기존 회원 여부 판단
    public ResponseDto SignupEmailCheck(String email, String snsId, String sns) {
        String id = sns + snsId;
        User user = userRepository.findBySnsId(id).orElse(null);

        if (user == null) {
            return new ResponseDto("추가 정보 작성이 필요한 사용자입니다.", new SnsInfoDto(email, id));
        } else {
            LoginReponseDto loginReponseDto = new LoginReponseDto(jwtTokenProvider.createToken(id), user.getEmail(), user.getNickname(), user.getId());
            return new ResponseDto("로그인이 완료되었습니다", loginReponseDto);
        }
    }

    //북마크 체크여부 판단
    @Transactional
    public ResponseDto bookmarkCheck(Long postId, String snsId) {
        // [예외처리] 북마크하고자 하는 post를 삭제 등과 같은 이유로 찾을 수 없을 때
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException(ErrorCode.NO_POST_ERROR)
        );
        User user = loadUserBySnsId(snsId);
        // [예외처리] 사용자가 자신의 게시물을 북마크하려고 할 때
        if (post.getUser().equals(user)) {
            throw new BadRequestException(ErrorCode.NO_BOOKMARK_MY_POST_ERROR);
        }
        Map<String, String> isBookmarkChecked = new HashMap<>();

        if (postService.isBookmarkChecked(post, user)) {
            bookmarkRepository.deleteByUserAndPost(user, post);
            isBookmarkChecked.put("isBookmarkChecked", "false");
            return new ResponseDto("북마크 삭제 성공", isBookmarkChecked);
        } else {
            bookmarkRepository.save(new Bookmark(user, post));
            isBookmarkChecked.put("isBookmarkChecked", "true");
            return new ResponseDto("북마크 추가 성공", isBookmarkChecked);
        }
    }

    //email, nickname, 기술스택 수정
    @Transactional
    public UserResponseDto updateUserInfo(String snsId, UserRequestDto userRequestDto) {

        String nickname = userRequestDto.getNickname();
        String email = userRequestDto.getEmail();
        User user = loadUserBySnsId(snsId);

        techStackRepository.deleteAllByUser(user);
        user.resetTechStack();

        if (!user.getNickname().equals(nickname)) {
            nicknameCheckByNickname(nickname);
        }
        if (!user.getEmail().equals(email)) {
            emailCheckByEmail(email);
        }

        techStackRepository.deleteAllByUser(user);
        List<TechStack> techStackList = techStackConverter.convertStringToTechStack(userRequestDto.getUserTechStack(), user, null);
        user.resetTechStack();
        user.updateUserInfo(email, nickname, techStackList);
        techStackRepository.saveAll(techStackList);
        return new UserResponseDto(user, techStackConverter.convertTechStackToString(user.getTechStackList()));
    }

    // 회원 소개 수정
    @Transactional
    public UserResponseDto updateUserDesc(String snsId, String userDesc) {
        User user = loadUserBySnsId(snsId);
        user.updateUserInfo(userDesc);

        return new UserResponseDto(user, techStackConverter.convertTechStackToString(user.getTechStackList()));
    }

    @Transactional
    public ResponseDto deleteUser(User user) {
        List<Post> postList = postRepository.findAllByUser(user);
        for (Post post : postList) {
            post.deleteUser(loadUnknownUser());
        }
        techStackRepository.deleteAllByUser(user);
        applicantRepository.deleteAllByUser(user);
        bookmarkRepository.deleteAllByUser(user);
        teamRepository.deleteAllByUser(user);

        userRepository.delete(user);

        return new ResponseDto("성공적으로 회원 정보가 삭제되었습니다.", "");
    }
}
