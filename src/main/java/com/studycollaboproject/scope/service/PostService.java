package com.studycollaboproject.scope.service;


import com.studycollaboproject.scope.dto.PostRequestDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.*;

import com.studycollaboproject.scope.dto.PostListDto;

import com.studycollaboproject.scope.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    @Transactional
    public Post writePost(PostRequestDto postRequestDto) {
        List<TechStack> techStackList = new ArrayList<>();
        String[] techList = postRequestDto.getTechStack().split(";");

        Post post = new Post(postRequestDto);
        for (String tech : techList) {
            TechStack techStack = new TechStack(Tech.techOf(tech), post);
            techStackRepository.save(techStack);
            techStackList.add(techStack);
        }
        post.updateTechStack(techStackList);
        return postRepository.save(post);
    }

    @Transactional
    public ResponseDto editPost(Long id, PostRequestDto postRequestDto){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("포스트가 존재하지 않습니다."));
        post.update(postRequestDto);
        return new ResponseDto("200", "", post);
    }

    @Transactional
    public boolean deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("포스트가 존재하지 않습니다."));
        postRepository.delete(post);
        return true;
    }


    public ResponseDto readPost(String filter,
                                int displayNumber,
                                int page,
                                String sort,
                                String nickname) {

        List<Post> filterPosts = new ArrayList<>();
        List<Tech>techList = new ArrayList<>();
        List<TechStack> techStackList;
        List<String> filterList;

        // 선택한 기술스택 있으면 filterPosts에 필터링된 값을 담아준다.
        if (filter != null && !filter.isEmpty()) {
            // String으로 받아온 filter 값을 세미콜론으로 스플릿
            // String[] splitStr = filter.split(";");
            // filterList = new ArrayList<>(Arrays.asList(splitStr));
            filterList = Arrays.asList(filter.split(";"));

            // 스플릿 한 값으로 techStack에서 검색
            for (String techFilter : filterList) {
                //프론트에서 받아온 String을 Enumtype으로 변경
                Tech tech = Tech.valueOf(techFilter);
                techList.add(tech);
            }
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

        List<Post> allPosts = new ArrayList<>();
        List<Post> posts = new ArrayList<>();

        switch (sort) {
            case "bookmark":
                List<Bookmark> bookmarkList = bookmarkRepository.findAllByPostInAndUserNickname(filterPosts, nickname);
                for (Bookmark bookmark : bookmarkList) {
                    Post post = bookmark.getPost();
                    allPosts.add(post);
                }
                break;
            case "createdAt":
                filterPosts.sort(Comparator.comparing(Post::getCreatedAt));
                break;

            case "deadline":
                filterPosts.sort(Comparator.comparing(Post::getStartDate, Comparator.reverseOrder()));
                break;
        }
        for (int i = 0; i < displayNumber; i++) {
            posts.add(allPosts.get(i));
        }
        int totalPost = allPosts.size();

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

    @Transactional
    public void updateUrl(String backUrl, String frontUrl, String nickname, Long postId){
        User user = userRepository.findByNickname(nickname).orElseThrow(
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
