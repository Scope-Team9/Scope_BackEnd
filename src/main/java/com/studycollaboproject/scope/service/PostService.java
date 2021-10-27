package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.PostReqeustDto;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.model.Bookmark;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Tech;
import com.studycollaboproject.scope.model.TechStack;
import com.studycollaboproject.scope.repository.BookmarkRepository;
import com.studycollaboproject.scope.repository.PostRepository;
import com.studycollaboproject.scope.repository.TechStackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    public final PostRepository postRepository;
    public final BookmarkRepository bookmarkRepository;
    public final TechStackRepository techStackRepository;

    public ResponseDto writePost(PostReqeustDto postReqeustDto) {
        Post post = new Post(postReqeustDto);
        postRepository.save(post);
        return new ResponseDto("200","","");
    }

    @Transactional
    public ResponseDto editPost(Long id, PostReqeustDto postReqeustDto){
        Post post = postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("포스트가 존재하지 않습니다."));
        post.update(postReqeustDto);
        return new ResponseDto("200", "", post);
    }

    @Transactional
    public ResponseDto deletePost(Long id) {
        postRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("포스트가 존재하지 않습니다."));
        postRepository.deleteById(id);
        return new ResponseDto("200", "","");
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

        // String으로 받아온 filter 값을 세미콜론으로 스플릿
        // String[] splitStr = filter.split(";");
        // filterList = new ArrayList<>(Arrays.asList(splitStr));
        filterList = Arrays.asList(filter.split(";"));

        // 스플릿 한 값으로 techStack에서 검색
        for(String techFilter:filterList)
        {
            //프론트에서 받아온 String을 Enumtype으로 변경
            Tech tech = Tech.valueOf(techFilter);
            techList.add(tech);
        }
        // techList에 포함된 TehcStack을 techStackList에 저장
        techStackList = techStackRepository.findAllByTechIn(techList);

        // 저장된 techStackList에서 post를 가져옴
        for (TechStack techStack : techStackList){
            Post post = techStack.getPost();
            filterPosts.add(post);
        }


        List<Post> allPosts = new ArrayList<>();
        List<Post> posts = new ArrayList<>();

        switch (sort){
               case "bookmark" :
                   List<Bookmark> bookmarkList = bookmarkRepository.findAllByPostInAndUserNickname(filterPosts,nickname);
                   for (Bookmark bookmark :bookmarkList){
                       Post post = bookmark.getPost();
                       allPosts.add(post);
                   }
                   break;

               case "latest":
                   allPosts = postRepository.findAllByOrderByCreatedAtDesc();
                   break;
           }

           for (int i=0; i< displayNumber; i++){
               posts.add(allPosts.get(i));
           }
        int totalPost = allPosts.size();

        return new ResponseDto("200", "success", posts);
    }


}