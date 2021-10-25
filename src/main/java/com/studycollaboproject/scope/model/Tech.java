package com.studycollaboproject.scope.model;

import lombok.Getter;

@Getter
public enum Tech {

    TECH_JAVA("Java"),
    TECH_JS("JavaScript"),
    TECH_RUBY("Ruby"),
    TECH_PYTHON("Python"),
    TECH_PHP("PHP"),
    TECH_MYSQL("MYSQL"),
    TECH_MONGODB("MongDB"),
    TECH_NODEJS("Node.js"),
    TECH_CPP("C++"),
    TECH_FLASK("Flask"),
    TECH_DJANGO("Django"),
    TECH_VUE("Vue.js"),
    TECH_REACT("React"),
    TECH_REACTNATIVE("React Native"),
    TECH_GO("Go"),
    TECH_SWIFT("Swift"),
    TECH_KOTLIN("Kotlin"),
    TECH_C("C"),
    TECH_TYPESCRIPT("TypeScript"),
    TECH_ANGULAR("Angular.js"),
    TECH_CSHARP("C#"),
    TECH_SPRING("Spring"),
    TECH_R("R"),
    TECH_JQUERY("jQuery");

    private final String tech;

    Tech(String tech){
        this.tech = tech;
    }
}
