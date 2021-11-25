package com.studycollaboproject.scope.webSocket;

import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.exception.BadRequestException;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.model.Applicant;
import com.studycollaboproject.scope.model.Message;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.redis.ConnectedUser;
import com.studycollaboproject.scope.redis.ConnectedUserRedisRepository;
import com.studycollaboproject.scope.repository.ApplicantRepository;
import com.studycollaboproject.scope.repository.MessageRepository;
import com.studycollaboproject.scope.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AlertService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ConnectedUserRedisRepository userRedisRepository;
    private final UserRepository userRepository;
    private final AlertRepository alertRepository;
    private final MessageRepository messageRepository;
    private final ApplicantRepository applicantRepository;



    // 특정 uuid로 실시간 알림
    public void alertToUser(User receivedUser, String message) {
        ConnectedUser connectedUser = userRedisRepository.findById(receivedUser.getNickname())
                .orElseThrow(() -> new BadRequestException(ErrorCode.NO_UUID_ERROR));
        String userUuid = connectedUser.getUserUuid();
        List<Alert> alerts = alertRepository
                .findAllByReceivedUserAndAlertCheckedFalse(receivedUser);
        Map<String, Object> result = new HashMap<>();
        result.put("alarmCount", alerts.size()+1);
        result.put("message", message);
        ResponseEntity<ResponseDto> response = new ResponseEntity<>(
                new ResponseDto("실시간 알림", result),
                HttpStatus.OK
        );
        simpMessagingTemplate.convertAndSendToUser(userUuid,"/sub/alert",response);
    }


    public void userConnect(ConnectedUser connectedUser) {
        //유저 연결 정보 방(redis)에 저장
        userRedisRepository.save(connectedUser);

    }

    public void userDisConnect(String nickname) {
        //방(redis)에 있는 유저 연결 정보 삭제
        User user = userRepository.findBySnsId(nickname).orElseThrow(()
                -> new BadRequestException(ErrorCode.NO_USER_ERROR));
        userRepository.deleteById(user.getId());
    }

    // 모든 알림 가져오기
    public Map<String, Object> getAlerts(User user) {
        List<Alert> alerts = alertRepository.findAllByReceivedUserOrderByCreatedAtDesc(user);
        List<AlertResponseDto> alertResponseDtos = new ArrayList<>();
        for (Alert alert : alerts){
            String messageDetail = getMessageDetail(alert);
            AlertResponseDto alertResponseDto = new AlertResponseDto(alert, messageDetail);
            alertResponseDtos.add(alertResponseDto);
        }


        Map<String, Object> Response = new HashMap<>();
        Response.put("alertResponseDtos",alertResponseDtos);
        Response.put("alarmCount",alertCount(user));

        return Response;
    }

    // 한개의 알림 읽기
    public Map<String, Object> getAlert(Long alertId, User user) {
        Alert alert = alertRepository.findById(alertId).orElseThrow(()
                -> new BadRequestException(ErrorCode.NO_ALERT_ERROR));
        alert.alertReadCheck();
        alertRepository.save(alert);
        String messageDetail = getMessageDetail(alert);
        AlertResponseDto alertResponseDto = new AlertResponseDto(alert, messageDetail);
        Map<String, Object> Response = new HashMap<>();
        Response.put("alertResponseDto",alertResponseDto);
        Response.put("alarmCount",alertCount(user));


        return Response;
    }

    // 알림 카운트
    public int alertCount(User user) {
        List<Alert> alerts = alertRepository.findAllByReceivedUserAndAlertCheckedFalse(user);
        return alerts.size();
    }

    // 알림 MessageDetail 가져오기
    private String getMessageDetail(Alert alert) {
        String messageDetail;
        Long messageId = alert.getMessageId();
        AlertType alertType = alert.getAlertType();
        if (alertType == AlertType.RECEIVE_MESSAGE) {
            Message message = messageRepository.findById(messageId).orElseThrow(
                    () -> new BadRequestException(ErrorCode.NO_MESSAGE_ERROR));
            messageDetail = "[message:" + message.getMessage() + "]";
        }
        else if (alertType == AlertType.NEW_APPLICANT){
            Applicant applicant = applicantRepository.findById(messageId).orElseThrow(
                    () -> new BadRequestException(ErrorCode.NO_APPLICANT_ERROR));
            messageDetail = "[ApplicantUser:" + applicant.getUser().getNickname() +"]";
        }
        else
        {
            messageDetail = null;
        }
        return messageDetail;
    }

    // Alert 저장

    public void saveAlert(String sentUserNickname, AlertType type, Long messageId, User receivedUser) {

        String alertMessage;
        AlertType alertType;
        if (type == AlertType.RECEIVE_MESSAGE)
        {
            alertMessage = sentUserNickname + "님의 쪽지가 도착했습니다.";
            alertType = AlertType.RECEIVE_MESSAGE;
        }
        else if(type == AlertType.NEW_APPLICANT)
        {
            alertMessage = sentUserNickname + "님이 프로젝트에 지원했습니다.";
            alertType = AlertType.NEW_APPLICANT;

        }

        else if(type == AlertType.END_PROJECT)
        {
            alertMessage = "프로젝트가 종료되었습니다.";
            alertType = AlertType.END_PROJECT;

        }
        else {
            alertMessage = sentUserNickname + "님의 팀에 지원이 승낙되었습니다.";
            alertType = AlertType.MATCH_TEAM;

        }

        Alert alert = new Alert(alertMessage, alertType, messageId, receivedUser);
        alertRepository.save(alert);
    }
}




