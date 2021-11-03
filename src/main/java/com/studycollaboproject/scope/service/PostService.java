package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.*;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.*;
import com.studycollaboproject.scope.repository.*;
import com.studycollaboproject.scope.util.TechStackConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final TechStackRepository techStackRepository;
    private final TeamRepository teamRepository;
    private final TechStackConverter techStackConverter;
    private final ApplicantRepository applicantRepository;
    private final UserRepository userRepository;
    private final TotalResultRepository totalResultRepository;


    @Transactional
    public PostResponseDto writePost(PostRequestDto postRequestDto, String snsId) {
        List<String> postTechStackList = postRequestDto.getTechStackList();
        User user = userRepository.findBySnsId(snsId).orElseThrow(() ->
                new RestApiException(ErrorCode.NO_USER_ERROR));

        Post post = new Post(postRequestDto, user);
        List<TechStack> techStackList = new ArrayList<>(techStackConverter.convertStringToTechStack(postTechStackList, null, post));
        teamRepository.save(new Team(user,post));
        techStackRepository.saveAll(techStackList);
        post.updateTechStack(techStackList);
        Post savedPost = postRepository.save(post);
        return new PostResponseDto(savedPost);
    }

    @Transactional
    public PostResponseDto editPost(Long postId, PostRequestDto postRequestDto, String snsId) {
        Post post = loadPostByPostId(postId);
        User user = post.getUser();
        if (user.getSnsId().equals(snsId)) {
            techStackRepository.deleteAllByPost(post);
            List<String> postTechStackList = postRequestDto.getTechStackList();
            List<TechStack> techStackList = new ArrayList<>(techStackConverter.convertStringToTechStack(postTechStackList, null, post));
            techStackRepository.saveAll(techStackList);
            post.updateTechStack(techStackList);
            post.update(postRequestDto);
            return new PostResponseDto(post);
        } else {
            throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
    }

    @Transactional
    public Long deletePost(Long postId, String snsId) {

        Post post = loadPostByPostId(postId);
        User user = post.getUser();

        if (user.getSnsId().equals(snsId)) {
            techStackRepository.deleteAllByPost(post);
            teamRepository.deleteAllByPost(post);
            bookmarkRepository.deleteAllByPost(post);
            applicantRepository.deleteAllByPost(post);
            postRepository.delete(post);
            return postId;
        } else {
            throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

    }


    public ResponseDto readPost(String filter,
                                int displayNumber,
                                int page,
                                String sort,
                                String snsId,
                                String bookmarkRecommend) {


        // 필터링 될 포스트배열
        List<Post> filterPosts = new ArrayList<>();
        List<TechStack> techStackList;
        // String으로 받아온 filter 값을 세미콜론으로 스플릿
        List<String> filterList = Arrays.asList(filter.split(";"));
        // 받아온 techStack 값을 List<Tech>로 전환
        List<Tech> techList = techStackConverter.convertStringToTech(filterList);
        // 반환될 포스트 배열
        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        // bookmarkRecommend가 recommend라면 추천 포스트만 리턴한다.
        if ("recommend".equals(bookmarkRecommend)) {
            List<String> propensityTypeList = getPropensityTypeList(snsId);
            for (String propensityType : propensityTypeList) {
                filterPosts.addAll(postRepository.findAllByUserMemberPropensityType(propensityType));
            }
            sendByDisplayNumber(displayNumber, page, filterPosts, snsId);
            return new ResponseDto("200", "success", postResponseDtos);
        }

        // bookmarkRecommend가 Bookmark라면 북마크 포스트만 리턴한다.
        else if ("bookmark".equals(bookmarkRecommend)) {
            List<Bookmark> bookmarkList = bookmarkRepository.findAllByUserNickname(snsId);
            for (Bookmark bookmark : bookmarkList) {
                Post post = bookmark.getPost();
                filterPosts.add(post);
            }
            sendByDisplayNumber(displayNumber, page, filterPosts, snsId);
            return new ResponseDto("200", "success", postResponseDtos);
        }


        // 선택한 기술스택 있으면 filterPosts에 필터링된 값을 담아준다.
        if (!filterList.isEmpty()) {

            // techList에 포함된 TehcStack을 techStackList에 저장
            techStackList = techStackRepository.findAllByTechIn(techList);

            // 저장된 techStackList에서 post를 가져옴
            for (TechStack techStack : techStackList) {
                Post post = techStack.getPost();
                filterPosts.add(post);
            }
        }
        // 선택된 기술스택이 없으면 전체  포스트를 담아준다.
        else {
            filterPosts = postRepository.findAll();
        }


        switch (sort) {

            case "createdAt":
                filterPosts.sort(Comparator.comparing(Post::getCreatedAt));
                break;

            case "deadline":
                filterPosts.sort(Comparator.comparing(Post::getStartDate));
                break;
        }


        // display number와 Page 사용해서 객체 수 만큼 넘기기
        postResponseDtos = sendByDisplayNumber(displayNumber, page, filterPosts, snsId);
        return new ResponseDto("200", "success", postResponseDtos);
    }


    public MypagePostListDto getPostList(User user) {
        List<Team> teamList = teamRepository.findAllByUser(user);

        List<PostResponseDto> inProgressList = new ArrayList<>();
        List<PostResponseDto> endList = new ArrayList<>();
        List<PostResponseDto> recruitmentList = new ArrayList<>();

        for (Team team : teamList) {
            switch (team.getPost().getProjectStatus().getProjectStatus()) {
                case "진행중":
                    inProgressList.add(new PostResponseDto(team.getPost()));
                    break;
                case "종료":
                    endList.add(new PostResponseDto(team.getPost()));
                    break;
                case "모집중":
                    recruitmentList.add(new PostResponseDto(team.getPost()));
                    break;
            }
        }

        return new MypagePostListDto(new UserResponseDto(user,techStackConverter.convertTechStackToString(user.getTechStackList())),recruitmentList, inProgressList, endList);
    }

    public List<String> getPropensityTypeList(String snsId) {
        User user = userRepository.findBySnsId(snsId).orElseThrow(() ->
                new RestApiException(ErrorCode.NO_USER_ERROR));

        String userPropensityType = user.getUserPropensityType();
        String memberPropensityType = user.getMemberPropensityType();
        List<TotalResult> totalResultList = totalResultRepository.findAllByUserType(userPropensityType);

        for (TotalResult totalResult : totalResultList) {
            if (totalResult.getMemberType().equals(memberPropensityType)) {
                Long result = totalResult.getResult()+1L;
                totalResult.setResult(result);
            }
        }

        totalResultList.sort(Comparator.comparing(TotalResult::getResult));

        List<String> sortedRecommededList = new ArrayList<>();
        for (TotalResult totalResult : totalResultList) {
            sortedRecommededList.add(totalResult.getMemberType());
        }

        return sortedRecommededList;
    }


    @Transactional
    public PostResponseDto updateUrl(String backUrl, String frontUrl, String snsId, Long postId) {
        User user = userRepository.findBySnsId(snsId).orElseThrow(() ->
                new RestApiException(ErrorCode.NO_USER_ERROR));
        Post post = loadPostByPostId(postId);
        if (isTeamStarter(post, snsId)) {
            Team team = loadTeamByUserAndPost(user, post);
            team.setUrl(frontUrl, backUrl);
        } else {
            throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        return new PostResponseDto(post);
    }

    public Team loadTeamByUserAndPost(User user, Post post) {
        return teamRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );
    }


    public Post loadPostByPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );
    }

    public Post loadPostIfOwner(Long postId, User user) {
        return postRepository.findByIdAndUser(postId, user).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR)
        );
    }

    public List<PostResponseDto> sendByDisplayNumber(int displayNumber, int page, List<Post> filterPosts, String snsId) {
        List<PostResponseDto> postResponseDtos = new ArrayList<>();
        int index = displayNumber * page;
        for (int i = index; i < index + displayNumber; i++) {
            if (filterPosts.size() == 0 || filterPosts.size() < index) {
                break;
            }
            boolean bookmarkChecked = isBookmarkChecked(filterPosts.get(i).getId(), snsId);
            PostResponseDto postResponseDto = new PostResponseDto(filterPosts.get(i), bookmarkChecked);
            postResponseDtos.add(postResponseDto);

        }
        return postResponseDtos;
    }

    @Transactional
    public PostResponseDto updateStatus(Long postId, String projectStatus, String snsId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );
        userRepository.findBySnsId(snsId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_USER_ERROR)
        );
        if (snsId.equals(post.getUser().getSnsId())) {
            post.updateStatus(projectStatus);
        } else {
            throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        return new PostResponseDto(post);
    }

    public boolean isTeamStarter(Post post, String snsId) {
        return post.getUser().getSnsId().equals(snsId);
    }

    // 현재 로그인 한 사용자 북마크 체크여부 확인
    public boolean isBookmarkChecked(Long postId, String username) {
        Optional<Bookmark> bookmark = bookmarkRepository.findByPostIdAndUserNickname(postId, username);
        return bookmark.isPresent();

    }
}