package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TechStackService {

    private final UserRepository userRepository;

}
