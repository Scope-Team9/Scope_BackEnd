package com.studycollaboproject.scope.model;

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

}
