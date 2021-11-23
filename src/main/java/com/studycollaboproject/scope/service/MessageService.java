package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.MessageRequestDto;
import com.studycollaboproject.scope.dto.MessageResponseDto;
import com.studycollaboproject.scope.exception.BadRequestException;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.model.Message;
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

    public MessageResponseDto sentMessage(MessageRequestDto messageRequestDto, String snsId) {
        User sentUser = userRepository.findBySnsId(snsId).orElseThrow(() ->
                new BadRequestException(ErrorCode.NO_USER_ERROR));
        Message message = new Message(messageRequestDto,sentUser);
        Message savedMessage = messageRepository.save(message);

        return new MessageResponseDto(savedMessage);
    }
}
