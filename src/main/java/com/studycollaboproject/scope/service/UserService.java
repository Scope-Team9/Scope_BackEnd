package com.studycollaboproject.scope.service;


import com.studycollaboproject.scope.dto.PostListDto;
import com.studycollaboproject.scope.model.Bookmark;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.BookmarkRepository;
import com.studycollaboproject.scope.repository.TeamRepository;
import com.studycollaboproject.scope.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final TeamRepository teamRepository;


    public User getUserInfo(String userNickname) {
        return userRepository.findByNickname(userNickname).orElseThrow(
                ()-> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

    }

    public List<Post> getBookmarkList(User user) {
        List<Bookmark> bookmarkList = bookmarkRepository.findAllByUser(user);
        List<Post> postList = new ArrayList<>();
        for (Bookmark bookmark : bookmarkList) {
            postList.add(bookmark.getPost());
        }
        return postList;
    }


    public PostListDto getPostList(User user, List<Post> bookmarkList){
        List<Team> teamList = teamRepository.findAllByUser(user);

        List<Post> inprogressList = new ArrayList<>();
        List<Post> endList = new ArrayList<>();
        List<Post> recruitmentList = new ArrayList<>();

        for (Team team : teamList) {
            switch (team.getPost().getProjectStatus().getProjectStatus()){
                case "진행중":
                    inprogressList.add(team.getPost());
                    break;
                case "종료":
                    endList.add(team.getPost());
                    break;
                case "모집중":
                    recruitmentList.add(team.getPost());
                    break;
            }
        }
        return new PostListDto(bookmarkList,recruitmentList,inprogressList,endList);
    }
}
