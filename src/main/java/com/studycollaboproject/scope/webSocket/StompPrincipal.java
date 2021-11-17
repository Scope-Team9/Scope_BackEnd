package com.studycollaboproject.scope.webSocket;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.security.Principal;

@AllArgsConstructor
@NoArgsConstructor
public class StompPrincipal implements Principal { //Principal - 웹 소켓 주체의 정보를 담고 있음

    String name;

    @Override
    public String getName() {
        return this.name;
    }
}
