package com.studycollaboproject.scope.webSocket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class ChatRoomController {
    private final ChatRoomRepository chatRoomRepository;

    //모든 룸 반환
//룸이 필요할까 고민해야함

}
