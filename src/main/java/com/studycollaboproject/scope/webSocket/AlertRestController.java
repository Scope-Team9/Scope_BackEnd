package com.studycollaboproject.scope.webSocket;

import com.studycollaboproject.scope.dto.ResponseDto;
import com.studycollaboproject.scope.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AlertRestController {
    private final AlertService alertService;

    @GetMapping("/alerts")
    public ResponseEntity<Object> getAlerts(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        Map<String, Object> alertResponseDtos = alertService.getAlerts(userDetails.getUser());
        return new ResponseEntity<>(
                new ResponseDto("모든 알림 조회 성공", alertResponseDtos),
                HttpStatus.OK
        );
    }

    @GetMapping("/alerts/{alertId}")
    public ResponseEntity<Object> getAlert(@PathVariable Long alertId, @AuthenticationPrincipal UserDetailsImpl userDetails){

        Map<String, Object>  alertResponseDto = alertService.getAlert(alertId, userDetails.getUser());
        return new ResponseEntity<>(
                new ResponseDto("알림 조회 성공", alertResponseDto),
                HttpStatus.OK
        );
    }

}
