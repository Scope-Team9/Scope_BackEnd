package com.studycollaboproject.scope.controller;

import com.studycollaboproject.scope.dto.PostRequestDto;
import com.studycollaboproject.scope.dto.SignupTestDto;
import com.studycollaboproject.scope.dto.TestUserSetupDto;
import com.studycollaboproject.scope.model.TechStack;
import com.studycollaboproject.scope.model.TotalResult;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.TechStackRepository;
import com.studycollaboproject.scope.repository.TotalResultRepository;
import com.studycollaboproject.scope.repository.UserRepository;
import com.studycollaboproject.scope.security.JwtTokenProvider;
import com.studycollaboproject.scope.service.PostService;
import com.studycollaboproject.scope.util.TechStackConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TestDataRunner implements ApplicationRunner {

    private final TotalResultRepository totalResultRepository;
    private final UserRepository userRepository;
    private final PostService postService;
    private final TechStackConverter techStackConverter;
    private final TechStackRepository techStackRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void run(ApplicationArguments args) {
        if (totalResultRepository.count() == 0L) {
            String[] propensityTypeList = {"LVG", "LVP", "LHG", "LHP", "FVG", "FVP", "FHG", "FHP", "RHP"};

            for (String s : propensityTypeList) {
                for (String s1 : propensityTypeList) {
                    TotalResult totalResult = new TotalResult(s, s1);
                    totalResultRepository.save(totalResult);
                }
            }
        }

        if (userRepository.findBySnsId("unknown").isEmpty()) {
            User user = new User(new TestUserSetupDto());
            userRepository.save(user);
        }
//        for (PostRequestDto requestDto : requestDtoList) {
//            System.out.println("requestDto = " + requestDto);
//        }
//        if(userRepository.count() > 50){
//            return;
//        }
//
//        List<String> propensityType = new ArrayList<>();
//
//        propensityType.add("LHP");
//        propensityType.add("LVP");
//        propensityType.add("LHG");
//        propensityType.add("LVG");
//
//        propensityType.add("FHP");
//        propensityType.add("FVP");
//        propensityType.add("FHG");
//        propensityType.add("FVG");
//        propensityType.add("RHP");
//
//
//        List<String> techStack = new ArrayList<>();
//        techStack.add("Java");
//        techStack.add("JavaScript");
//        techStack.add("Python");
//        techStack.add("Node");
//        techStack.add("cpp");
//        techStack.add("Flask");
//        techStack.add("Django");
//        techStack.add("React");
//        techStack.add("php");
//        techStack.add("Swift");
//        techStack.add("Kotlin");
//        techStack.add("TypeScript");
//        techStack.add("Spring");

//        List<List<String>> techStackList = new ArrayList<>();

//        for (int i = 0; i < techStack.size(); i++) {
//            for (int j = i + 1; j < techStack.size(); j++) {
//                List<String> techStackStringList = new ArrayList<>();
//                techStackStringList.add(techStack.get(i));
//                techStackStringList.add(techStack.get(j));
//
//                techStackList.add(techStackStringList);
//            }
//        }

//        List<User> userList = new ArrayList<>();
//
//        int index1 = 0;
//        int index2 = 0;
//        int index3 = 0;
//        for(int i = 0 ;i < 100; i++){
//            String nickname = "user" + (i + 3);
//            String email = nickname + "@mail.com";
//
//            index1 = (int)((Math.random() * 100) % propensityType.size());
//            index2 = (int)((Math.random() * 100) % propensityType.size());
//            SignupTestDto signupTestDto = new SignupTestDto(
//                    nickname,
//                    email,
//                    nickname,
//                    propensityType.get(index1),
//                    propensityType.get(index2)
//                    );
//            String token = jwtTokenProvider.createToken(signupTestDto.getSnsId());
//            User user = new User(signupTestDto,token.split("\\.")[1]);
//            userRepository.save(user);
//
//            List<TechStack> techStacks = new ArrayList<>();
//            for (int j = 0; j < techStackList.get(index3).size(); j++) {
//                TechStack techStack1 = new TechStack(techStackConverter.convertStringToTech(techStackList.get(index3)).get(j), user);
//                techStacks.add(techStack1);
//                techStackRepository.save(techStack1);
//            }
//            String token1 = jwtTokenProvider.createToken(user.getSnsId());
//            user.addTechStackListAndToken(techStacks,token1);
//            userRepository.save(user);
//            userList.add(user);
//        }
//
//        List<String> online = new ArrayList<>();
//        List<String> term = new ArrayList<>();
//        List<String> subject = new ArrayList<>();
//        List<String> person = new ArrayList<>();
//        List<String> want = new ArrayList<>();
//        List<Integer> member = new ArrayList<>();
//
//        online.add("온라인으로만 ");
//        online.add("오프라인으로 ");
//        online.add("온라인 및 오프라인 ");
//        online.add("비대면으로 ");
//        online.add("오프라인으로도 가능하고 ");
//
//        term.add("짧게 ");
//        term.add("3개월간 ");
//        term.add("6개월간 ");
//        term.add("1년간 ");
//        term.add("방학동안 ");
//
//        subject.add("프로젝트 ");
//        subject.add("스프링 ");
//        subject.add("노드 ");
//        subject.add("리액트 ");
//        subject.add("웹개발 ");
//        subject.add("iOS ");
//        subject.add("안드로이드 ");
//        subject.add("서버 ");
//        subject.add("프론트앤드 ");
//        subject.add("백앤드 ");
//
//        person.add("고수님 ");
//        person.add("함께 공부하실 분 ");
//        person.add("같이 하실 분 ");
//        person.add("초보자도 ");
//        person.add("능력자 ");
//        person.add("아무나 ");
//        person.add("팀원 ");
//        person.add("주니어 개발자 ");
//
//        want.add("구해요.");
//        want.add("찾아요.");
//        want.add("함께해요.");
//        want.add("모십니다.");
//        want.add("오세요.");
//
//        member.add(6);
//        member.add(5);
//        member.add(4);
//        member.add(3);
//        member.add(2);
//
////        List<PostRequestDto> requestDtoList = new ArrayList<>();
//        for(String on : online) {
//            for (String ter : term) {
//                for (String sub : subject) {
//                    for (String per : person) {
//                        for (String wan : want) {
//                            index1 = (int) ((Math.random() * 100) % member.size());
//                            index2 = (int) ((Math.random() * 100) % techStackList.size());
//                            index3 = (int) ((Math.random() * 100) % userList.size());
//                            String title = on + ter + sub + per + wan;
//                            String summary = per + wan;
//                            int totalMember = member.get(index1);
//                            String contents = on + ter + sub + per + totalMember + "명 " + wan;
//                            int period1 = (int) ((Math.random() * 100) % 20);
//                            int period2 = (int) (Math.random() * 100);
//
//                            LocalDate startDate = LocalDate.now().plusDays(period1);
//                            LocalDate endDate = LocalDate.now().plusDays(period2);
//                            PostRequestDto postRequestDto = new PostRequestDto(
//                                    title,
//                                    summary,
//                                    contents,
//                                    totalMember,
//                                    "모집중",
//                                    startDate.toString(),
//                                    endDate.toString(),
//                                    techStackList.get(index2)
//                            );
////                        requestDtoList.add(postRequestDto);
//
//                            postService.writePost(postRequestDto, userList.get(index3).getSnsId());
//                        }
//                    }
//                }
//            }
//        }

        /*
        랜덤 데이터 100개 생성
         */
//        List<String> techStack = new ArrayList<>();
//        techStack.add("Java");
//        techStack.add("JavaScript");
//        techStack.add("Python");
//        techStack.add("Node");
//        techStack.add("cpp");
//        techStack.add("Flask");
//        techStack.add("Django");
//        techStack.add("React");
//        techStack.add("php");
//        techStack.add("Swift");
//        techStack.add("Kotlin");
//        techStack.add("TypeScript");
//        techStack.add("Spring");
//        List<String> online = new ArrayList<>();
//        List<String> term = new ArrayList<>();
//        List<String> subject = new ArrayList<>();
//        List<String> person = new ArrayList<>();
//        List<String> want = new ArrayList<>();
//        List<Integer> member = new ArrayList<>();
//
//        online.add("온라인으로만 ");
//        online.add("오프라인으로 ");
//        online.add("온라인 및 오프라인 ");
//        online.add("비대면으로 ");
//        online.add("오프라인으로도 가능하고 ");
//
//        term.add("짧게 ");
//        term.add("3개월간 ");
//        term.add("6개월간 ");
//        term.add("1년간 ");
//        term.add("방학동안 ");
//
//        subject.add("프로젝트 ");
//        subject.add("스프링 ");
//        subject.add("노드 ");
//        subject.add("리액트 ");
//        subject.add("웹개발 ");
//        subject.add("iOS ");
//        subject.add("안드로이드 ");
//        subject.add("서버 ");
//        subject.add("프론트앤드 ");
//        subject.add("백앤드 ");
//
//        person.add("고수님 ");
//        person.add("함께 공부하실 분 ");
//        person.add("같이 하실 분 ");
//        person.add("초보자도 ");
//        person.add("능력자 ");
//        person.add("아무나 ");
//        person.add("팀원 ");
//        person.add("주니어 개발자 ");
//
//        want.add("구해요.");
//        want.add("찾아요.");
//        want.add("함께해요.");
//        want.add("모십니다.");
//        want.add("오세요.");
//
//        member.add(6);
//        member.add(5);
//        member.add(4);
//        member.add(3);
//        member.add(2);
//        List<User> curUserList = userRepository.findAll();
//        for (int i = 0; i < 100; i++) {
//            String on = online.get(getRandomIndex(online.size()));
//            String ter = term.get(getRandomIndex(term.size()));
//            String sub = subject.get(getRandomIndex(subject.size()));
//            String per = person.get(getRandomIndex(person.size()));
//            String wan = want.get(getRandomIndex(want.size()));
//            String title = on + ter + sub + per + wan;
//            String summary = per + wan;
//            int totalMember = member.get(getRandomIndex(member.size()));
//            String contents = on + ter + sub + per + totalMember + "명 " + wan;
//            LocalDate startDate = LocalDate.now().plusDays(getRandomIndex(20));
//            LocalDate endDate = LocalDate.now().plusDays(getRandomIndex(100));
//            Set<String> randomTechStack = new HashSet<>();
//            int techCount = getRandomIndex(4);
//            while(randomTechStack.size() < techCount){
//                randomTechStack.add(techStack.get(getRandomIndex(techStack.size())));
//            }
//            PostRequestDto postRequestDto = new PostRequestDto(
//                    title,
//                    summary,
//                    contents,
//                    totalMember,
//                    "모집중",
//                    startDate.toString(),
//                    endDate.toString(),
//                    new ArrayList<>(randomTechStack)
//            );
//
//            postService.writePost(postRequestDto, curUserList.get(getRandomIndex(curUserList.size())).getSnsId());
//        }
    }

    private int getRandomIndex(int size) {
        return (int) ((Math.random() * 100) % size);
    }
}
