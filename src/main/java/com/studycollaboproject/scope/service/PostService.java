package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.*;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.*;
import com.studycollaboproject.scope.repository.*;
import com.studycollaboproject.scope.util.TechStackConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

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
        teamRepository.save(new Team(user, post));
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


    public Map<String,Object> readPost(String filter,
                                          int displayNumber,
                                          int page,
                                          String sort,
                                          String snsId,
                                          String bookmarkRecommend) {


        // 필터링 될 포스트배열
        List<Post> filterPosts = new ArrayList<>();
        // String으로 받아온 filter 값을 세미콜론으로 스플릿
        List<String> filterList = Arrays.stream(filter.split(";")).filter(o -> !o.equals("")).collect(Collectors.toList());
        // 받아온 techStack 값을 List<Tech>로 전환
        List<Tech> techList = techStackConverter.convertStringToTech(filterList);
        Pageable pageable = PageRequest.of(page, displayNumber);
        Map<String, Object> postsAndTotalPage = new HashMap<>();

        // bookmarkRecommend가 recommend라면 추천 포스트만 리턴한다.
        if ("recommend".equals(bookmarkRecommend)) {
            List<String> propensityTypeList = getPropensityTypeList(snsId);
            if ("deadline".equals(sort)) {
                for (String propensity : propensityTypeList) {
                    filterPosts.addAll(postRepository.findAllByPropensityTypeOrderByStartDate(propensity));
                }
            } else {
                for (String propensity : propensityTypeList) {
                    filterPosts.addAll(postRepository.findAllByPropensityTypeOrderByCreatedAt(propensity));
                }
            }
            return sendByDisplayNumber(displayNumber, page, filterPosts, snsId);
        }
        // bookmarkRecommend가 Bookmark라면 북마크 포스트만 리턴한다.
        else if ("bookmark".equals(bookmarkRecommend)) {
            int totalPostSize = postRepository.findAllBookmarkByUserSnsId(snsId).size();
            int totalPage = (int) Math.ceil( (double) totalPostSize/displayNumber);
            postsAndTotalPage.put("totalPage", totalPage);
            if ("deadline".equals(sort)) {
                filterPosts = postRepository.findAllByBookmarkOrderByStartDate(snsId, pageable);
            } else {
                filterPosts = postRepository.findAllByBookmarkOrderByCreatedAt(snsId, pageable);
            }

            List<PostResponseDto> postResponseDtos = filterPosts.stream().map(o -> new PostResponseDto(o, true)).collect(Collectors.toList());
            postsAndTotalPage.put("postResponseDtos", postResponseDtos);
            return postsAndTotalPage;
        }
        // 전체 조회의 경우.
        else {
            int totalPostSize = postRepository.findAllByTechStackList_TechIn(techList).size();
            int totalPage = (int) Math.ceil( (double) totalPostSize/displayNumber);
            postsAndTotalPage.put("totalPage", totalPage);

            if ("deadline".equals(sort)) {
                filterPosts = postRepository.findAllByTechInOrderByStartDate(techList, pageable);
            } else {
                filterPosts = postRepository.findAllByTechInOrderByCreatedAt(techList, pageable);
            }
            if (snsId.equals("")) {
                List<PostResponseDto> postResponseDtos = filterPosts.stream().map(o-> new PostResponseDto(o, false)).collect(Collectors.toList());
                postsAndTotalPage.put("postResponseDtos", postResponseDtos);
                return  postsAndTotalPage;
            }

            List<Post> bookmarkList = postRepository.findAllBookmarkByUserSnsId(snsId);
            List<PostResponseDto> postResponseDtos = filterPosts.stream().map(o -> new PostResponseDto(o , checkBookmark(o, bookmarkList))).collect(Collectors.toList());
            postsAndTotalPage.put("postResponseDtos", postResponseDtos);
            return postsAndTotalPage;
        }
    }

    private boolean checkBookmark(Post post, List<Post> bookmarkList) {
        for (Post bookmarkPost : bookmarkList) {
            if (bookmarkPost.getId().equals(post.getId())) {
                return true;
            }
        }
        return false;
    }

    public MypagePostListDto getMyPostList(User user) {
        List<Post> includePostList = postRepository.findAllByUserSnsId(user.getSnsId());
        List<Post> bookmarkPostList = postRepository.findAllBookmarkByUserSnsId(user.getSnsId());
        List<PostResponseDto> myBookmarkList = bookmarkPostList.stream().map(o -> new PostResponseDto(o, true)).collect(Collectors.toList());
        List<PostResponseDto> includedList = includePostList.stream().map(o -> new PostResponseDto(o, checkBookmark(o, bookmarkPostList))).collect(Collectors.toList());

        return new MypagePostListDto(includedList, myBookmarkList, new UserResponseDto(user, techStackConverter.convertTechStackToString(user.getTechStackList())));
    }

    public List<String> getPropensityTypeList(String snsId) {
        User user = userRepository.findBySnsId(snsId).orElseThrow(() ->
                new RestApiException(ErrorCode.NO_USER_ERROR));

        String userPropensityType = user.getUserPropensityType();
        String memberPropensityType = user.getMemberPropensityType();
        List<TotalResult> totalResultList = totalResultRepository.findAllByUserType(userPropensityType);

        for (TotalResult totalResult : totalResultList) {
            if (totalResult.getMemberType().equals(memberPropensityType)) {
                Long result = totalResult.getResult() + 1L;
                totalResult.setResult(result);
            }
        }

        totalResultList.sort((o1, o2) -> o2.getResult().compareTo(o1.getResult()));

        List<String> sortedRecommendedList = new ArrayList<>();
        for (TotalResult totalResult : totalResultList) {
            sortedRecommendedList.add(totalResult.getMemberType());
        }

        return sortedRecommendedList;
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

    public Map<String, Object> sendByDisplayNumber(int displayNumber, int page, List<Post> filterPosts, String snsId) {
        List<Post> bookmarkList = postRepository.findAllBookmarkByUserSnsId(snsId);
        int index = displayNumber * page;
        int toIndex = Math.min(filterPosts.size(), index + displayNumber);

        List<PostResponseDto> postResponseDtos = filterPosts.subList(index, toIndex)
                .stream().map(o -> new PostResponseDto(o, checkBookmark(o, bookmarkList))).collect(Collectors.toList());
        int totalPostSize = filterPosts.size();
        int totalPage = (int) Math.ceil( (double) totalPostSize/displayNumber);

        Map<String, Object> postsAndTotalPage = new HashMap<>();
        postsAndTotalPage.put("postResponseDtos", postResponseDtos);
        postsAndTotalPage.put("totalPage", totalPage);
        return postsAndTotalPage;
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
    public boolean isBookmarkChecked(Post post, User user) {
        Optional<Bookmark> bookmark = bookmarkRepository.findByPostAndUser(post, user);
        return bookmark.isPresent();

    }
}