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
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.PostRepository;
import com.studycollaboproject.scope.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssessmentService {

    private final PostRepository postRepository;
    private final TeamRepository teamRepository;

    public ResponseDto assessmentMember(Long postId, User user, List<Long> userIds) throws JsonProcessingException {
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

    private ResponseDto getAssessmentResult(String rater, List<String> userList) throws JsonProcessingException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/json");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("rater", rater);
        body.addAll("userList", userList);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> assessmentRequest = new HttpEntity<>(body,headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "http://127.0.0.1:5000/api/rating",
                HttpMethod.POST,
                assessmentRequest,
                String.class
        );
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        String status = jsonNode.get("status").asText();
        String msg = jsonNode.get("msg").asText();

        return new ResponseDto(status,msg,"");
    }


}
