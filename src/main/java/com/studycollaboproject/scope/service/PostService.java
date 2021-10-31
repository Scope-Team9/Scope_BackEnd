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
    public Post writePost(PostRequestDto postRequestDto, String snsId) {
        String[] techList = postRequestDto.getTechStack().split(";");
        List<String > stringList = Arrays.asList(techList);
        User user = userRepository.findBySnsId(snsId).orElseThrow(()->
                new RestApiException(ErrorCode.NO_USER_ERROR));

        Post post = new Post(postRequestDto,user);


        List<TechStack> techStackList = new ArrayList<>(techStackConverter.convertStringToTechStack(stringList, post.getUser()));

        techStackRepository.saveAll(techStackList);
        post.updateTechStack(techStackList);
        postRepository.save(post);
        return post;
    }

    @Transactional
    public ResponseDto editPost(Long postId, PostRequestDto postRequestDto, String snsId) {
        Post post = loadPostByPostId(postId);
        User user = post.getUser();
        if (user.getSnsId().equals(snsId)){
            post.update(postRequestDto);
            PostResponseDto postResponseDto = new PostResponseDto(post);
            return new ResponseDto("200", "", postResponseDto);
        }else{
            throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }
    }

    @Transactional
    public ResponseDto deletePost(Long postId, String snsId) {

        Post post = loadPostByPostId(postId);
        User user = post.getUser();

        if (user.getSnsId().equals(snsId)){
            techStackRepository.deleteAllByPost(post);
            teamRepository.deleteAllByPost(post);
            bookmarkRepository.deleteAllByPost(post);
            applicantRepository.deleteAllByPost(post);
            postRepository.delete(post);
            return new ResponseDto("200", "", "");
        }else{
            throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

    }


    public ResponseDto readPost(String filter,
                                int displayNumber,
                                int page,
                                String sort,
                                String snsId) {

        // 필터링 될 포스트배열
        List<Post> filterPosts = new ArrayList<>();
        // 반환될 포스트 배열
        List<Post> posts = new ArrayList<>();
        // 잠시 북마크가 담길 포스트 배열
        List<Post> filterTemp = new ArrayList<>();
        List<Tech> techList;
        List<TechStack> techStackList;
        List<String> filterList;

        // 선택한 기술스택 있으면 filterPosts에 필터링된 값을 담아준다.
        if (filter != null && !filter.isEmpty()) {
            // String으로 받아온 filter 값을 세미콜론으로 스플릿
            // String[] splitStr = filter.split(";");
            // filterList = new ArrayList<>(Arrays.asList(splitStr));
            filterList = Arrays.asList(filter.split(";"));

            // 받아온 techStack 값을 List<Tech>로 전환
            techList = techStackConverter.convertStringToTech(filterList);

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
            case "bookmark":
                List<Bookmark> bookmarkList = bookmarkRepository.findAllByPostInAndUserNickname(filterPosts, snsId);
                for (Bookmark bookmark : bookmarkList) {
                    Post post = bookmark.getPost();
                    filterTemp.add(post);

                    // 필터포스트 재정의
                    filterPosts = filterTemp;
                }
                break;
            case "createdAt":
                filterPosts.sort(Comparator.comparing(Post::getCreatedAt));
                break;

            case "deadline":
                filterPosts.sort(Comparator.comparing(Post::getStartDate, Comparator.reverseOrder()));
                break;

            case "recommend":
                List<String> propensityTypeList = getPropensityTypeList(snsId);

                for(String propensityType : propensityTypeList){
                    filterPosts.addAll(postRepository.findByUserMemberPropensityType(propensityType));
                }

        }



        // display number와 Page 사용해서 객체 수 만큼 넘기기
        int index = displayNumber * page;

        for (int i = index; i < index + displayNumber; i++) {
            if(filterPosts.size() ==0 || filterPosts.size() < index ){
                break;
            }

            posts.add(filterPosts.get(i));
        }
                return new ResponseDto("200", "success", posts);
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

        return new MypagePostListDto(new UserResponseDto(user),recruitmentList, inProgressList, endList);
    }

    public List<String> getPropensityTypeList(String snsId) {
        User user = userRepository.findBySnsId(snsId).orElseThrow(()->
                new RestApiException(ErrorCode.NO_USER_ERROR));

        String userPropensityType = user.getUserPropensityType();
        String memberPropensityType = user.getMemberPropensityType();
        List<TotalResult> totalResultList = totalResultRepository.findAllByUserType(userPropensityType);

        for (TotalResult totalResult : totalResultList) {
            if (totalResult.getMemberType().equals(memberPropensityType)){
                totalResult.addrecommended();
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
    public void updateUrl(String backUrl, String frontUrl, String snsId, Long postId){
        User user = userRepository.findBySnsId(snsId).orElseThrow(()->
                new RestApiException(ErrorCode.NO_USER_ERROR));
        Post post = loadPostByPostId(postId);
        if (isTeamStarter(post,snsId)){
            Team team = loadTeamByUserAndPost(user,post);
            team.setUrl(frontUrl, backUrl);
        }else {
            throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

    }

    public Team loadTeamByUserAndPost(User user, Post post){
        return teamRepository.findByUserAndPost(user,post).orElseThrow(
                ()->new RestApiException(ErrorCode.NO_POST_ERROR)
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

    @Transactional
    public void updateStatus(Long postId, ProjectStatus projectStatus, String snsId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );
        userRepository.findBySnsId(snsId).orElseThrow(
                ()-> new RestApiException(ErrorCode.NO_USER_ERROR)
        );
        if (snsId.equals(post.getUser().getSnsId())){
            post.updateStatus(projectStatus);
        }else {
            throw new RestApiException(ErrorCode.NO_AUTHORIZATION_ERROR);
        }

    }

    public boolean isTeamStarter(Post post, String snsId) {
        return post.getUser().getSnsId().equals(snsId);
    }

    // 현재 로그인 한 사용자 북마크 체크여부 확인
    public boolean isBookmarkChecked(Long postId, String username) {
        Optional<Bookmark> bookmark= bookmarkRepository.findByPostIdAndUserNickname(postId,username);
        return bookmark.isPresent();

        }
    }