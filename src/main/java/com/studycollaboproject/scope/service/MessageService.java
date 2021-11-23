package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.MessageRequestDto;
import com.studycollaboproject.scope.exception.BadRequestException;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.MessageRepository;
import com.studycollaboproject.scope.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageRequestDto sentMessage(MessageRequestDto messageRequestDto, String snsId) {
        String receivedId = messageRequestDto.getReceivedId();
        User sentUser = userRepository.findBySnsId(snsId).orElseThrow(() ->
                new BadRequestException(ErrorCode.NO_USER_ERROR));
        User receivedUser = userRepository.findBySnsId(receivedId).orElseThrow(() ->
                new BadRequestException(ErrorCode.NO_USER_ERROR));
    }
}
