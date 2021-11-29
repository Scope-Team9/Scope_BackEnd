package com.studycollaboproject.scope.util;

import com.studycollaboproject.scope.model.Post;
import com.studycollaboproject.scope.model.Tech;
import com.studycollaboproject.scope.model.TechStack;
import com.studycollaboproject.scope.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class TechStackConverter {

    public List<TechStack> convertStringToTechStack(List<String> techStack, User user, Post post) {
        List<TechStack> techStackList = new ArrayList<>();
        for (String tech : techStack) {
            switch (tech) {
                case "Java":
                    if(user == null){
                        techStackList.add(new TechStack(Tech.TECH_JAVA, post));
                    } else {
                        techStackList.add(new TechStack(Tech.TECH_JAVA, user));
                    }
                    break;
                case "JavaScript":
                    if(user == null){
                        techStackList.add(new TechStack(Tech.TECH_JS, post));
                    } else {
                        techStackList.add(new TechStack(Tech.TECH_JS, user));
                    }
                    break;
                case "Python":
                    if(user == null){
                        techStackList.add(new TechStack(Tech.TECH_PYTHON, post));
                    } else {
                        techStackList.add(new TechStack(Tech.TECH_PYTHON, user));
                    }
                    break;
                case "Node":
                    if(user == null){
                        techStackList.add(new TechStack(Tech.TECH_NODEJS, post));
                    } else {
                        techStackList.add(new TechStack(Tech.TECH_NODEJS, user));
                    }
                    break;
                case "C++":
                    if(user == null){
                        techStackList.add(new TechStack(Tech.TECH_CPP, post));
                    } else {
                        techStackList.add(new TechStack(Tech.TECH_CPP, user));
                    }
                    break;
                case "Flask":
                    if(user == null){
                        techStackList.add(new TechStack(Tech.TECH_FLASK, post));
                    } else {
                        techStackList.add(new TechStack(Tech.TECH_FLASK, user));
                    }
                    break;
                case "Django":
                    if(user == null){
                        techStackList.add(new TechStack(Tech.TECH_DJANGO, post));
                    } else {
                        techStackList.add(new TechStack(Tech.TECH_DJANGO, user));
                    }
                    break;
                case "Vue":
                    if(user == null){
                        techStackList.add(new TechStack(Tech.TECH_VUE, post));
                    } else {
                        techStackList.add(new TechStack(Tech.TECH_VUE, user));
                    }
                    break;
                case "React":
                    if(user == null){
                        techStackList.add(new TechStack(Tech.TECH_REACT, post));
                    } else {
                        techStackList.add(new TechStack(Tech.TECH_REACT, user));
                    }
                    break;
                case "php":
                    if(user == null){
                        techStackList.add(new TechStack(Tech.TECH_PHP, post));
                    } else {
                        techStackList.add(new TechStack(Tech.TECH_PHP, user));
                    }
                    break;
                case "Swift":
                    if(user == null){
                        techStackList.add(new TechStack(Tech.TECH_SWIFT, post));
                    } else {
                        techStackList.add(new TechStack(Tech.TECH_SWIFT, user));
                    }
                    break;
                case "Kotlin":
                    if(user == null){
                        techStackList.add(new TechStack(Tech.TECH_KOTLIN, post));
                    } else {
                        techStackList.add(new TechStack(Tech.TECH_KOTLIN, user));
                    }
                    break;
                case "TypeScript":
                    if(user == null){
                        techStackList.add(new TechStack(Tech.TECH_TYPESCRIPT, post));
                    } else {
                        techStackList.add(new TechStack(Tech.TECH_TYPESCRIPT, user));
                    }
                    break;
                case "Spring":
                    if(user == null){
                        techStackList.add(new TechStack(Tech.TECH_SPRING, post));
                    } else {
                        techStackList.add(new TechStack(Tech.TECH_SPRING, user));
                    }
                    break;
            }
        }
        return techStackList;
    }

    public List<Tech> convertStringToTech(List<String> techStack) {
        List<Tech> techList = new ArrayList<>();
        for (String tech : techStack) {
            switch (tech) {
                case "Java":
                    techList.add(Tech.TECH_JAVA);
                    break;
                case "JavaScript":
                    techList.add(Tech.TECH_JS);
                    break;
                case "Python":
                    techList.add(Tech.TECH_PYTHON);
                    break;
                case "Node":
                    techList.add(Tech.TECH_NODEJS);
                    break;
                case "C++":
                    techList.add(Tech.TECH_CPP);
                    break;
                case "Flask":
                    techList.add(Tech.TECH_FLASK);
                    break;
                case "Django":
                    techList.add(Tech.TECH_DJANGO);
                    break;
                case "Vue":
                    techList.add(Tech.TECH_VUE);
                    break;
                case "React":
                    techList.add(Tech.TECH_REACT);
                    break;
                case "php":
                    techList.add(Tech.TECH_PHP);
                    break;
                case "Swift":
                    techList.add(Tech.TECH_SWIFT);
                    break;
                case "Kotlin":
                    techList.add(Tech.TECH_KOTLIN);
                    break;
                case "TypeScript":
                    techList.add(Tech.TECH_TYPESCRIPT);
                    break;
                case "Spring":
                    techList.add(Tech.TECH_SPRING);
                    break;
            }
        }
        if(techList.size() == 0){
            Collections.addAll(techList, Tech.values());
        }
        return techList;
    }

    public List<String> convertTechStackToString(List<TechStack> techStack) {
        List<String> techList = new ArrayList<>();
        for (TechStack stack : techStack) {
            techList.add(stack.getTech().getTech());
        }
        return techList;
    }
}
