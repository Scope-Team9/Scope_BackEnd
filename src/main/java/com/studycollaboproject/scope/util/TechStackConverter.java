package com.studycollaboproject.scope.util;

import com.studycollaboproject.scope.model.Tech;
import com.studycollaboproject.scope.model.TechStack;
import com.studycollaboproject.scope.model.User;

import java.util.ArrayList;
import java.util.List;

public class TechStackConverter {

    public List<TechStack> convertStringToTechStack(List<String> techStack, User user) {
        List<TechStack> TechStackList = new ArrayList<>();
        for (String tech : techStack) {
            switch (tech) {
                case "java":
                    TechStackList.add(new TechStack(Tech.TECH_JAVA, user));
                    break;
                case "JavaScript":
                    TechStackList.add(new TechStack(Tech.TECH_JS, user));
                    break;
                case "Python":
                    TechStackList.add(new TechStack(Tech.TECH_PYTHON, user));
                    break;
                case "Node.js":
                    TechStackList.add(new TechStack(Tech.TECH_NODEJS, user));
                    break;
                case "C++":
                    TechStackList.add(new TechStack(Tech.TECH_CPP, user));
                    break;
                case "Flask":
                    TechStackList.add(new TechStack(Tech.TECH_FLASK, user));
                    break;
                case "Django":
                    TechStackList.add(new TechStack(Tech.TECH_DJANGO, user));
                    break;
                case "Vue.js":
                    TechStackList.add(new TechStack(Tech.TECH_VUE, user));
                    break;
                case "React":
                    TechStackList.add(new TechStack(Tech.TECH_REACT, user));
                    break;
                case "React Native":
                    TechStackList.add(new TechStack(Tech.TECH_REACTNATIVE, user));
                    break;
                case "PHP":
                    TechStackList.add(new TechStack(Tech.TECH_PHP, user));
                    break;
                case "Swift":
                    TechStackList.add(new TechStack(Tech.TECH_SWIFT, user));
                    break;
                case "Kotlin":
                    TechStackList.add(new TechStack(Tech.TECH_KOTLIN, user));
                    break;
                case "TypeScript":
                    TechStackList.add(new TechStack(Tech.TECH_TYPESCRIPT, user));
                    break;
                case "Angular.js":
                    TechStackList.add(new TechStack(Tech.TECH_ANGULAR, user));
                    break;
                case "Spring":
                    TechStackList.add(new TechStack(Tech.TECH_SPRING, user));
                    break;
            }
        }
        return TechStackList;
    }

    public List<Tech> convertStringToTech(List<String> techStack) {
        List<Tech> TechList = new ArrayList<>();
        for (String tech : techStack) {
            if (tech.equals("")) {
                continue;
            }
            switch (tech) {
                case "java":
                    TechList.add(Tech.TECH_JAVA);
                    break;
                case "JavaScript":
                    TechList.add(Tech.TECH_JS);
                    break;
                case "Python":
                    TechList.add(Tech.TECH_PYTHON);
                    break;
                case "Node.js":
                    TechList.add(Tech.TECH_NODEJS);
                    break;
                case "C++":
                    TechList.add(Tech.TECH_CPP);
                    break;
                case "Flask":
                    TechList.add(Tech.TECH_FLASK);
                    break;
                case "Django":
                    TechList.add(Tech.TECH_DJANGO);
                    break;
                case "Vue.js":
                    TechList.add(Tech.TECH_VUE);
                    break;
                case "React":
                    TechList.add(Tech.TECH_REACT);
                    break;
                case "React Native":
                    TechList.add(Tech.TECH_REACTNATIVE);
                    break;
                case "PHP":
                    TechList.add(Tech.TECH_PHP);
                    break;
                case "Swift":
                    TechList.add(Tech.TECH_SWIFT);
                    break;
                case "Kotlin":
                    TechList.add(Tech.TECH_KOTLIN);
                    break;
                case "TypeScript":
                    TechList.add(Tech.TECH_TYPESCRIPT);
                    break;
                case "Angular.js":
                    TechList.add(Tech.TECH_ANGULAR);
                    break;
                case "Spring":
                    TechList.add(Tech.TECH_SPRING);
                    break;
            }
        }
        return TechList;
    }
}
