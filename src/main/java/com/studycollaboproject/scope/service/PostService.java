package com.studycollaboproject.scope.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studycollaboproject.scope.dto.PostRequestDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.*;

import com.studycollaboproject.scope.dto.PostListDto;

import com.studycollaboproject.scope.repository.*;
import com.studycollaboproject.scope.util.TechStackConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.*;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final BookmarkRepository bookmarkRepository;
    private final TechStackRepository techStackRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TechStackConverter techStackConverter;
    private final ApplicantRepository applicantRepository;


    @Transactional
    public Post writePost(PostRequestDto postRequestDto) {
        List<TechStack> techStackList = new ArrayList<>();
        String[] techList = postRequestDto.getTechStack().split(";");
        List<String > stringList = Arrays.asList(techList);

        Post post = new Post(postRequestDto);

        techStackList.addAll(techStackConverter.convertStringToTechStack(stringList,post.getUser()));

        post.updateTechStack(techStackList);
        return postRepository.save(post);
    }

    @Transactional
    public ResponseDto editPost(Long id, PostRequestDto postRequestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("포스트가 존재하지 않습니다."));
        post.update(postRequestDto);
        return new ResponseDto("200", "", post);
    }

    @Transactional
    public boolean deletePost(Long id) {

        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("포스트가 존재하지 않습니다."));

        techStackRepository.deleteAllByPost(post);
        teamRepository.deleteAllByPost(post);
        bookmarkRepository.deleteAllByPost(post);
        applicantRepository.deleteAllByPost(post);
        postRepository.delete(post);

        return true;
    }


    public ResponseDto readPost(String filter,
                                int displayNumber,
                                int page,
                                String sort,
                                String snsId) throws JsonProcessingException {

        // 필터링 될 포스트배열
        List<Post> filterPosts = new ArrayList<>();
        // 반환될 포스트 배열
        List<Post> posts = new ArrayList<>();
        // 잠시 북마크가 담길 포스트 배열
        List<Post> filterTemp = new ArrayList<>();
        List<Tech> techList = new ArrayList<>();
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
                List<String> PropensityTypeList = getPropensityTypeList(snsId);


        }

                // display number와 Page 사용해서 객체 수 만큼 넘기기
                int index = displayNumber * page;
                for (int i = index; i < index + displayNumber; i++) {
                    if (filterPosts.size() < i) {
                        break;
                    }
                    posts.add(filterPosts.get(i));
                }


                return new ResponseDto("200", "success", posts);

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

    public List<String> getPropensityTypeList(String snsId) throws JsonProcessingException {
        User user = userRepository.findBySnsId(snsId).orElseThrow(
                () -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));
        String userPropensityType = user.getUserPropensityType();
        String memberPropensityType = user.getMemberPropensityType();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> assessmentRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://flaskServer/api/user/propensity-type?userPropensityType=" + userPropensityType + "&memberPropensityType=" + memberPropensityType,
                HttpMethod.GET,
                assessmentRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("data").findValuesAsText("recommendedPropensityType");
    }



    @Transactional
    public void updateUrl(String backUrl, String frontUrl, String snsId, Long postId){
        User user = userRepository.findBySnsId(snsId).orElseThrow(
                ()-> new IllegalArgumentException("해당 유저를 찾을 수 없습니다.")
        );
        Post post = postRepository.findById(postId).orElseThrow(
                ()->new IllegalArgumentException("해당 게시물을 찾을 수 없습니다.")
        );
        Team team = teamRepository.findByUserAndPost(user,post).orElseThrow(
                ()->new IllegalArgumentException("해당 게시물을 찾을 수 없습니다.")
        );
        team.setUrl(frontUrl, backUrl);

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
    public void updateStatus(Long postId, ProjectStatus projectStatus) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );
        post.updateStatus(projectStatus);
    }
}
