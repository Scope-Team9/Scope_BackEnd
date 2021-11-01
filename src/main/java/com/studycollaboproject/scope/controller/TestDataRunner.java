package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.TestUserSetupDto;
import com.studycollaboproject.scope.model.TotalResult;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.TotalResultRepository;
import com.studycollaboproject.scope.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TestDataRunner implements ApplicationRunner {

    private final TotalResultRepository totalResultRepository;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (totalResultRepository.count() == 0L) {
            String[] propersityTypeList = {"LVG", "LVP", "LHG", "LHP", "FVG", "FVP", "FHG", "FHP"};

            for (String s : propersityTypeList) {
                for (String s1 : propersityTypeList) {
                    TotalResult totalResult = new TotalResult(s, s1);
                    totalResultRepository.save(totalResult);
                }
            }
        }


        User user = new User(new TestUserSetupDto());
        userRepository.save(user);
    }
}
