package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.model.Tech;
import com.studycollaboproject.scope.model.TechStack;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TechStackService {

    private final UserRepository userRepository;

}
