package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.*;
import com.studycollaboproject.scope.exception.BadRequestException;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.ForbiddenException;
import com.studycollaboproject.scope.model.*;
import com.studycollaboproject.scope.repository.*;
import com.studycollaboproject.scope.util.TechStackConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
    public Post writePost(PostRequestDto postRequestDto, String snsId) {
        User user = userRepository.findBySnsId(snsId).orElseThrow(() ->
                new BadRequestException(ErrorCode.NO_USER_ERROR));
        vaildationDate(postRequestDto.getStartDate(), postRequestDto.getEndDate());
        Post post = new Post(postRequestDto, user);
        Set<String> techStackStringList = new HashSet<>(postRequestDto.getTechStackList());
        List<TechStack> techStackList = techStackConverter.convertStringToTechStack(new ArrayList<>(techStackStringList), null, post);
        teamRepository.save(new Team(user, post));
        techStackRepository.saveAll(techStackList);
        post.updateTechStack(techStackList);
        return postRepository.save(post);
    }

    private void vaildationDate(Timestamp start, Timestamp end) {
        if (start.compareTo(end) > -1) {
            throw new BadRequestException(ErrorCode.INVALID_INPUT_ERROR);
        }

    }

    @Transactional
    public PostResponseDto editPost(Long postId, PostRequestDto postRequestDto, String snsId) {
        Post post = loadPostByPostId(postId);
        User user = post.getUser();
        if (user.getSnsId().equals(snsId)) {
            techStackRepository.deleteAllByPost(post);
            Set<String> techStackStringList = new HashSet<>(postRequestDto.getTechStackList());
            List<TechStack> techStackList = techStackConverter.convertStringToTechStack(new ArrayList<>(techStackStringList), null, post);
            post.updateTechStack(techStackList);
            post.update(postRequestDto);
            techStackRepository.saveAll(techStackList);
            return new PostResponseDto(post);
        } else {
            throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);
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
            throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

    }
    @Transactional
    public void adminDeletePost(Long postId) {
        Post post = loadPostByPostId(postId);
        techStackRepository.deleteAllByPost(post);
        teamRepository.deleteAllByPost(post);
        bookmarkRepository.deleteAllByPost(post);
        applicantRepository.deleteAllByPost(post);
        postRepository.delete(post);
    }

    @Cacheable(cacheNames = "Post")
    public List<PostResponseDto> readPostPage(String filter,
                                              String sort,
                                              int page,
                                              String snsId,
                                              String bookmarkRecommend) {
        final int SIZE = 500;
        Pageable pageRequest = PageRequest.of(page, SIZE);
        long start = System.currentTimeMillis(); // ???????????? ??????
        // ????????? ??? ???????????????
        List<Post> filterPosts;
        // String?????? ????????? filter ?????? ?????????????????? ?????????
        List<String> filterList = Arrays.stream(filter.split(";")).filter(o -> !o.equals("")).collect(Collectors.toList());
        // Post ??? Id List
        List<Long> postIdList = new ArrayList<>();

        // bookmarkRecommend??? recommend?????? ?????? ???????????? ????????????.
        if ("recommend".equals(bookmarkRecommend)) {
            List<String> propensityTypeList = getPropensityTypeList(snsId);
            List<TechStack> userTechStackList = techStackRepository.findAllByUser_SnsId(snsId);
            List<String> userTechStackStringList = techStackConverter.convertTechStackToString(userTechStackList);
            List<Tech> techList = techStackConverter.convertStringToTech(userTechStackStringList);
            for (String propensity : propensityTypeList) {
                List<Long> tempPostIdList = postRepository.findAllPostIdListByPropensityType(propensity, techList, snsId, pageRequest);
                postIdList.addAll(tempPostIdList);
            }
            if ("deadline".equals(sort)) {
                filterPosts = postRepository.findByInPostIdOrderByStartDate(postIdList);
            } else {
                filterPosts = postRepository.findByInPostIdOrderByModifiedAt(postIdList);
            }

            List<Post> bookmarkList = postRepository.findAllBookmarkByUserSnsId(snsId);

            long end = System.currentTimeMillis();
            log.info("[{}], read??? Cache ???????????? : {} ", MDC.get("UUID"), end - start);
            return filterPosts.stream().map(o -> new PostResponseDto(o, hasPostFromPostList(o.getId(), bookmarkList))).collect(Collectors.toList());
        }

        // bookmarkRecommend??? Bookmark?????? ????????? ???????????? ????????????.
        else if ("bookmark".equals(bookmarkRecommend)) {
            postIdList = postRepository.findAllPostIdListByBookmark(snsId, pageRequest);

            if ("deadline".equals(sort)) {
                filterPosts = postRepository.findByInPostIdOrderByStartDate(postIdList);
            } else {
                filterPosts = postRepository.findByInPostIdOrderByModifiedAt(postIdList);
            }
            long end = System.currentTimeMillis();
            log.info("[{}], read??? Cache ???????????? : {} ", MDC.get("UUID"), end - start);

            return filterPosts.stream().map(o -> new PostResponseDto(o, true)).collect(Collectors.toList());
        }
        // ?????? ????????? ??????.
        else {
            if ("deadline".equals(sort)) {
                postIdList = postRepository.findAllPostIdListOrderByStartDate(pageRequest);
                filterPosts = postRepository.findByInPostIdOrderByStartDate(postIdList);
//                pageRequest = PageRequest.of(page, SIZE, Sort.by("ProjectStatus").descending().and(Sort.by("StartDate").ascending()));
//                filterPosts = postRepository.findAll(pageRequest).getContent();
            } else {
                postIdList = postRepository.findAllPostIdListOrderByModifiedAt(pageRequest);
                filterPosts = postRepository.findByInPostIdOrderByModifiedAt(postIdList);
            }
            if (snsId.equals("")) {
                long end = System.currentTimeMillis();
                log.info("[{}], read??? Cache ???????????? : {} ", MDC.get("UUID"), end - start);
                return filterPosts.stream().map(o -> new PostResponseDto(o, false)).collect(Collectors.toList());
            }

            List<Post> bookmarkList = postRepository.findAllBookmarkByUserSnsId(snsId);
            long end = System.currentTimeMillis();
            log.info("[{}], read??? Cache ???????????? : {} ", MDC.get("UUID"), end - start);
            //            List<Post> bookmarkList = postRepository.findAllByBookmarkList_User_SnsIdOrderByStartDate(snsId);
            return filterPosts.stream().map(o -> new PostResponseDto(o, hasPostFromPostList(o.getId(), bookmarkList))).collect(Collectors.toList());
        }
    }

    @Cacheable(cacheNames = "Post")
    public List<PostResponseDto> readPost(String filter,
                                          String sort,
                                          String snsId,
                                          String bookmarkRecommend) {

        long start = System.currentTimeMillis(); // ???????????? ??????
        // ????????? ??? ???????????????
        List<Post> filterPosts = new ArrayList<>();
        // String?????? ????????? filter ?????? ?????????????????? ?????????
        List<String> filterList = Arrays.stream(filter.split(";")).filter(o -> !o.equals("")).collect(Collectors.toList());
        // ????????? techStack ?????? List<Tech>??? ??????
//        List<Tech> techList = techStackConverter.convertStringToTech(filterList);

        // bookmarkRecommend??? recommend?????? ?????? ???????????? ????????????.
        if ("recommend".equals(bookmarkRecommend)) {
            List<String> propensityTypeList = getPropensityTypeList(snsId);
            List<TechStack> userTechStackList = techStackRepository.findAllByUser_SnsId(snsId);
            List<String> userTechStackStringList = techStackConverter.convertTechStackToString(userTechStackList);
            List<Tech> techList = techStackConverter.convertStringToTech(userTechStackStringList);
            if ("deadline".equals(sort)) {
                for (String propensity : propensityTypeList) {
                    filterPosts.addAll(postRepository.findAllByPropensityTypeOrderByStartDate(propensity, techList, snsId));
//                    filterPosts.addAll(postRepository.findDistinctByUser_UserPropensityTypeAndProjectStatusAndUserSnsIdIsNotAndTechStackList_TechInOrderByStartDate(propensity, ProjectStatus.PROJECT_STATUS_RECRUITMENT, "unknown", techList));
                }
            } else {
                for (String propensity : propensityTypeList) {
                    filterPosts.addAll(postRepository.findAllByPropensityTypeOrderByModifiedAt(propensity, techList, snsId));
//                    filterPosts.addAll(postRepository.findDistinctByUser_UserPropensityTypeAndProjectStatusAndUserSnsIdIsNotAndTechStackList_TechInOrderByCreatedAtDesc(propensity, ProjectStatus.PROJECT_STATUS_RECRUITMENT, "unknown", techList));
                }
            }

            List<Post> bookmarkList = postRepository.findAllBookmarkByUserSnsId(snsId);
            //            List<Post> bookmarkList = postRepository.findAllByBookmarkList_User_SnsIdOrderByStartDate(snsId);
//            List<Post> bookmarkList = postRepository.findAllByBookmarkList_User_SnsIdOrderByStartDate(snsId);
            long end = System.currentTimeMillis();
            log.info("[{}], read??? Cache ???????????? : {} ", MDC.get("UUID"), end - start);
            return filterPosts.stream().map(o -> new PostResponseDto(o, hasPostFromPostList(o.getId(), bookmarkList))).collect(Collectors.toList());
        }

        // bookmarkRecommend??? Bookmark?????? ????????? ???????????? ????????????.
        else if ("bookmark".equals(bookmarkRecommend)) {

            if ("deadline".equals(sort)) {
                filterPosts = postRepository.findAllByBookmarkOrderByStartDate(snsId);
//                filterPosts = postRepository.findAllByBookmarkList_User_SnsIdOrderByStartDate(snsId);
            } else {
                filterPosts = postRepository.findAllByBookmarkOrderByModifiedAt(snsId);
//                filterPosts = postRepository.findAllByBookmarkList_User_SnsIdOrderByCreatedAtDesc(snsId);
            }
            long end = System.currentTimeMillis();
            log.info("[{}], read??? Cache ???????????? : {} ", MDC.get("UUID"), end - start);

            return filterPosts.stream().map(o -> new PostResponseDto(o, true)).collect(Collectors.toList());
        }
        // ?????? ????????? ??????.
        else {
            if ("deadline".equals(sort)) {
                filterPosts = postRepository.findAllOrderByStartDate();
//                filterPosts = postRepository.findDistinctByTechStackList_TechInOrderByStartDate(techList);
            } else {
                filterPosts = postRepository.findAllOrderByModifiedAt();
//                filterPosts = postRepository.findDistinctByTechStackList_TechInOrderByCreatedAtDesc(techList);
            }
            if (snsId.equals("")) {
                long end = System.currentTimeMillis();
                log.info("[{}], read??? Cache ???????????? : {} ", MDC.get("UUID"), end - start);
                return filterPosts.stream().map(o -> new PostResponseDto(o, false)).collect(Collectors.toList());
            }

            List<Post> bookmarkList = postRepository.findAllBookmarkByUserSnsId(snsId);
            long end = System.currentTimeMillis();
            log.info("[{}], read??? Cache ???????????? : {} ", MDC.get("UUID"), end - start);
            //            List<Post> bookmarkList = postRepository.findAllByBookmarkList_User_SnsIdOrderByStartDate(snsId);
            return filterPosts.stream().map(o -> new PostResponseDto(o, hasPostFromPostList(o.getId(), bookmarkList))).collect(Collectors.toList());
        }
    }

    public boolean hasPostFromUserBookmarkList(Post post, String snsId) {
        List<Post> bookmarkList = postRepository.findAllBookmarkByUserSnsId(snsId);
        //            List<Post> bookmarkList = postRepository.findAllByBookmarkList_User_SnsIdOrderByStartDate(snsId);
        return hasPostFromPostList(post.getId(), bookmarkList);
    }

    public boolean hasPostFromPostList(Long postId, List<Post> postList) {
        for (Post p : postList) {
            if (p.getId().equals(postId)) {
                return true;
            }
        }
        return false;
    }

    //????????? ???????????? ??????
    @Transactional
    public ResponseDto bookmarkPost(Long postId, String snsId) {
        // [????????????] ?????????????????? ?????? post??? ?????? ?????? ?????? ????????? ?????? ??? ?????? ???
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException(ErrorCode.NO_POST_ERROR)
        );
        User user = userRepository.findBySnsId(snsId).orElseThrow(
                () -> new BadRequestException(ErrorCode.NO_USER_ERROR)
        );
        // [????????????] ???????????? ????????? ???????????? ?????????????????? ??? ???
        if (post.getUser().equals(user)) {
            throw new BadRequestException(ErrorCode.NO_BOOKMARK_MY_POST_ERROR);
        }
        Map<String, String> isBookmarkChecked = new HashMap<>();
        Bookmark bookmark = bookmarkRepository.findByPostAndUser(post, user).orElseGet(() -> new Bookmark(user, post));
        if (bookmark.getId() != null) {
            bookmark.delete();
            bookmarkRepository.delete(bookmark);
            isBookmarkChecked.put("isBookmarkChecked", "false");
            return new ResponseDto("????????? ?????? ??????", isBookmarkChecked);
        } else {
            bookmarkRepository.save(bookmark);
            isBookmarkChecked.put("isBookmarkChecked", "true");
            return new ResponseDto("????????? ?????? ??????", isBookmarkChecked);
        }
    }

    public MypageResponseDto getMyPostList(User user, String loginUserSnsId) {
        List<Post> includePostList = postRepository.findMemberPostByUserSnsId(user.getSnsId());
        List<Post> readyPostList = postRepository.findReadyPostByUserSnsId(user.getSnsId());
        List<Post> bookmarkPostList = postRepository.findAllBookmarkByUserSnsId(user.getSnsId());
//        List<Post> includePostList = postRepository.findAllByUser(user);
//        List<Post> bookmarkPostList = postRepository.findAllByBookmarkList_User_SnsIdOrderByStartDate(user.getSnsId());
        List<PostResponseDto> readyList = readyPostList.stream().map(o -> new PostResponseDto(o, true, loginUserSnsId)).collect(Collectors.toList());
        List<PostResponseDto> myBookmarkList = bookmarkPostList.stream().map(o -> new PostResponseDto(o, true, loginUserSnsId)).collect(Collectors.toList());
        List<PostResponseDto> includedList = includePostList.stream().map(o -> new PostResponseDto(o, hasPostFromPostList(o.getId(), bookmarkPostList), loginUserSnsId)).collect(Collectors.toList());

        return new MypageResponseDto(includedList, readyList, myBookmarkList, new UserResponseDto(user, techStackConverter.convertTechStackToString(user.getTechStackList())), loginUserSnsId.equals(user.getSnsId()));
    }

    public List<String> getPropensityTypeList(String snsId) {
        User user = userRepository.findBySnsId(snsId).orElseThrow(() ->
                new BadRequestException(ErrorCode.NO_USER_ERROR));

        String userPropensityType = user.getUserPropensityType();
        String memberPropensityType = user.getMemberPropensityType();

        // ????????? ????????? ?????? ????????? ????????? ?????? ??????
        List<TotalResult> totalResultList = totalResultRepository.findAllByUserType(userPropensityType);

        totalResultList.sort((o1, o2) -> o2.getResult().compareTo(o1.getResult()));
        List<String> sortedRecommendedList = new ArrayList<>();

        sortedRecommendedList.add(memberPropensityType);

        for (TotalResult totalResult : totalResultList) {
            if (!totalResult.getMemberType().equals(memberPropensityType)) {
                sortedRecommendedList.add(totalResult.getMemberType());
            }
        }

        return sortedRecommendedList;
    }


    @Transactional
    public PostResponseDto updateUrl(String backUrl, String frontUrl, String snsId, Long postId) {
        User user = userRepository.findBySnsId(snsId).orElseThrow(() ->
                new BadRequestException(ErrorCode.NO_USER_ERROR));
        Post post = loadPostByPostId(postId);
        if (isTeamStarter(post, snsId)) {
            Team team = loadTeamByUserAndPost(user, post);
            team.setUrl(frontUrl, backUrl);
        } else {
            throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        return new PostResponseDto(post);
    }

    public Team loadTeamByUserAndPost(User user, Post post) {
        return teamRepository.findByUserAndPost(user, post).orElseThrow(
                () -> new BadRequestException(ErrorCode.NO_POST_ERROR)
        );
    }


    public Post loadPostByPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException(ErrorCode.NO_POST_ERROR)
        );
    }

    public Post loadPostIfOwner(Long postId, User user) {
        return postRepository.findByIdAndUser(postId, user).orElseThrow(
                () -> new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR)
        );
    }

    @Transactional
    public PostResponseDto updateStatus(Long postId, String projectStatus, String snsId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new BadRequestException(ErrorCode.NO_POST_ERROR)
        );
        userRepository.findBySnsId(snsId).orElseThrow(
                () -> new BadRequestException(ErrorCode.NO_USER_ERROR)
        );
        if (snsId.equals(post.getUser().getSnsId())) {
            post.updateStatus(projectStatus);
        } else {
            throw new ForbiddenException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
        return new PostResponseDto(post);
    }

    public boolean isTeamStarter(Post post, String snsId) {
        return post.getUser().getSnsId().equals(snsId);
    }

    public List<PostResponseDto> searchPost(String snsId, String keyword, String sort) {
        List<Post> searchPostList;
        if (sort.equals("deadline")) {
            searchPostList = postRepository.findAllByKeywordOrderByStartDate(keyword);
        } else {
            searchPostList = postRepository.findAllByKeywordOrderByModifiedAt(keyword);
        }
        List<Post> bookmarkList = postRepository.findAllBookmarkByUserSnsId(snsId);
        //            List<Post> bookmarkList = postRepository.findAllByBookmarkList_User_SnsIdOrderByStartDate(snsId);
        return searchPostList.stream().map(o -> new PostResponseDto(o, hasPostFromPostList(o.getId(), bookmarkList))).collect(Collectors.toList());
    }
}