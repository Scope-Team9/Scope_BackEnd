package com.studycollaboproject.scope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
<<<<<<< HEAD

@Getter
=======
import lombok.Setter;

@Getter
@Setter
>>>>>>> feature/post
@AllArgsConstructor
public class ResponseDto {
    private String status;
    private String msg;
    private Object data;


}