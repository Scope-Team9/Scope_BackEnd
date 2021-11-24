package com.studycollaboproject.scope.webSocket;

import com.studycollaboproject.scope.dto.MessageRequestDto;
import com.studycollaboproject.scope.exception.BadRequestException;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.redis.ConnectedUser;
import com.studycollaboproject.scope.redis.ConnectedUserRedisRepository;
import com.studycollaboproject.scope.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AlertService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ConnectedUserRedisRepository userRedisRepository;
    private final UserRepository userRepository;

    // 메세지 보낼 시 특정 uuid로 알림
    public void sendMsg(MessageRequestDto messageRequestDto) {
        Optional<ConnectedUser> connectedUser = userRedisRepository.findById(messageRequestDto.getReceivedNickname());
//        connectedUser.ifPresent();

//                .orElseThrow(()
//                -> new BadRequestException(ErrorCode.NO_USER_ERROR));
//        String userUuid = connectedUser.getUserUuid();
//        simpMessagingTemplate.convertAndSendToUser(userUuid,"/sub/alert","새로운 메세지 : " + messageRequestDto.getMessage());

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
}
