package com.studycollaboproject.scope.model;

import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.RestApiException;
import lombok.Getter;

@Getter
public enum Tech {

    TECH_JAVA("Java"),
    TECH_JS("JavaScript"),
    TECH_PYTHON("Python"),
    TECH_NODEJS("Node.js"),
    TECH_CPP("C++"),
    TECH_FLASK("Flask"),
    TECH_DJANGO("Django"),
    TECH_VUE("Vue.js"),
    TECH_REACT("React"),
    TECH_REACTNATIVE("React Native"),
    TECH_PHP("PHP"),
    TECH_SWIFT("Swift"),
    TECH_KOTLIN("Kotlin"),
    TECH_TYPESCRIPT("TypeScript"),
    TECH_ANGULAR("Angular.js"),
    TECH_SPRING("Spring");

    private final String tech;

    Tech(String tech){
        this.tech = tech;
    }

    public static Tech techOf(String techString) {
        for (Tech tech : Tech.values()) {
            if (techString.equals(tech.getTech()))
            {
                return tech;
            }
        }
        throw new RestApiException(ErrorCode.NO_MATCH_ITEM_ERROR);
    }
}
