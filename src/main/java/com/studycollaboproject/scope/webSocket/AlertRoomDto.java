//package com.studycollaboproject.scope.webSocket;
//
//import lombok.Builder;
//import lombok.Getter;
//import org.springframework.web.socket.WebSocketSession;
//
//import java.util.HashSet;
//import java.util.Set;
//import java.util.UUID;
//
//// 주고받는 유저들을 모으는 방
//@Getter
//public class AlertRoomDto {
//
//    private String roomId;
//
//
//    public static AlertRoomDto create() {
//        AlertRoomDto alertRoomDto = new AlertRoomDto();
//        alertRoomDto.roomId= UUID.randomUUID().toString();
//        return alertRoomDto;
//
//    }
//
//    //alert 핸들링
//    public void handleAction(AlertDto alertDto,AlertService alertService){
//
//        switch (alertDto.getAlertType()){
//            case MATCH_TEAM:
//                alertDto.setMsg(alertDto.getName()+"님과 같은 팀이 되었습니다!");
//                break;
//            case END_PROJECT:
//                alertDto.setMsg("프로젝트가 종료되었습니다! 팀원 평가를 해주세요!");
//                break;
//            case NEW_APPLICANT:
//                alertDto.setMsg(alertDto.getName()+"님이 프로젝트에 지원했습니다!");
//                break;
//        }
//    }
//
//
//}
