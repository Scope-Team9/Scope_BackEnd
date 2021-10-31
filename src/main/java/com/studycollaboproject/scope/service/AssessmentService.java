package com.studycollaboproject.scope.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.dto.SnsInfoDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Team;
import com.studycollaboproject.scope.model.TotalResult;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.PostRepository;
import com.studycollaboproject.scope.repository.TeamRepository;
import com.studycollaboproject.scope.repository.TotalResultRepository;
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
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final PostRepository postRepository;
    private final TeamRepository teamRepository;
    private final TotalResultRepository totalResultRepository;

    public ResponseDto assessmentMember(Long postId, User user, List<Long> userIds) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RestApiException(ErrorCode.NO_POST_ERROR)
        );
        List<Team> member = teamRepository.findAllByPost(post);
        List<String> userList = new ArrayList<>();

        for (int i = 0; i < member.size(); i++) {
            if(member.get(i).getUser().getId().equals(userIds.get(i))){
                userList.add(member.get(i).getUser().getUserPropensityType());    //user -> member.get(i) 추천
            }
        }
        String rater = user.getUserPropensityType();
        return getAssessmentResult(rater,userList);

    }

    @Transactional
    public ResponseDto getAssessmentResult(String rater, List<String> userList) {

        for (String member : userList) {
            TotalResult totalResult = totalResultRepository.findByUserTypeAndMemberType(rater,member);
            totalResult.addrecommended();
        }
        return new ResponseDto("200","추천 결과가 저장되었습니다.","");
    }


}
