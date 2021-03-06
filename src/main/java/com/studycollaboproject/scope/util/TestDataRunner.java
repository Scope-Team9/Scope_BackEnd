//package com.studycollaboproject.scope.util;
//
//import com.studycollaboproject.scope.dto.PostRequestDto;
//import com.studycollaboproject.scope.dto.SignupTestDto;
//import com.studycollaboproject.scope.dto.TestUserSetupDto;
//import com.studycollaboproject.scope.model.TechStack;
//import com.studycollaboproject.scope.model.TotalResult;
//import com.studycollaboproject.scope.model.User;
//import com.studycollaboproject.scope.repository.TechStackRepository;
//import com.studycollaboproject.scope.repository.TotalResultRepository;
//import com.studycollaboproject.scope.repository.UserRepository;
//import com.studycollaboproject.scope.security.JwtTokenProvider;
//import com.studycollaboproject.scope.service.PostService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.sql.Timestamp;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//@RequiredArgsConstructor
//@Component
//public class TestDataRunner implements ApplicationRunner {
//
//    private final TotalResultRepository totalResultRepository;
//    private final UserRepository userRepository;
//    private final PostService postService;
//    private final TechStackConverter techStackConverter;
//    private final TechStackRepository techStackRepository;
//    private final JwtTokenProvider jwtTokenProvider;
//
//    @Override
//    public void run(ApplicationArguments args) {
//        if (totalResultRepository.count() == 0L) {
//            String[] propensityTypeList = {"LVG", "LVP", "LHG", "LHP", "FVG", "FVP", "FHG", "FHP", "RHP"};
//
//            for (String s : propensityTypeList) {
//                for (String s1 : propensityTypeList) {
//                    TotalResult totalResult = new TotalResult(s, s1);
//                    totalResultRepository.save(totalResult);
//                }
//            }
//        }
//
//        if (userRepository.findBySnsId("unknown").isEmpty()) {
//            User user = new User(new TestUserSetupDto());
//            userRepository.save(user);
//        }
////        for (PostRequestDto requestDto : requestDtoList) {
////            System.out.println("requestDto = " + requestDto);
////        }
////        if(userRepository.count() > 50){
////            return;
////        }
////
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
//        List<String> TechStack = new ArrayList<>();
//        TechStack.add("Java");
//        TechStack.add("JavaScript");
//        TechStack.add("Python");
//        TechStack.add("Node");
//        TechStack.add("cpp");
//        TechStack.add("Flask");
//        TechStack.add("Django");
//        TechStack.add("React");
//        TechStack.add("php");
//        TechStack.add("Swift");
//        TechStack.add("Kotlin");
//        TechStack.add("TypeScript");
//        TechStack.add("Spring");
//
//        List<List<String>> techStackList = new ArrayList<>();
//
//        for (int i = 0; i < TechStack.size(); i++) {
//            for (int j = i + 1; j < TechStack.size(); j++) {
//                List<String> techStackStringList = new ArrayList<>();
//                techStackStringList.add(TechStack.get(i));
//                techStackStringList.add(TechStack.get(j));
//
//                techStackList.add(techStackStringList);
//            }
//        }
//
//        List<User> userList = new ArrayList<>();
//
//        int index1 = 0;
//        int index2 = 0;
//        int index3 = 0;
//        for (int i = 0; i < 100; i++) {
//            String nickname = "user" + (i + 3);
//            String email = nickname + "@mail.com";
//
//            index1 = (int) ((Math.random() * 100) % propensityType.size());
//            index2 = (int) ((Math.random() * 100) % propensityType.size());
//            SignupTestDto signupTestDto = new SignupTestDto(
//                    nickname,
//                    email,
//                    nickname,
//                    propensityType.get(index1),
//                    propensityType.get(index2)
//            );
//            String token = jwtTokenProvider.createToken(signupTestDto.getSnsId());
//            User user = new User(signupTestDto, token.split("\\.")[1]);
//            userRepository.save(user);
//
//            List<TechStack> techStacks = new ArrayList<>();
//            for (int j = 0; j < techStackList.get(index3).size(); j++) {
//                TechStack techStack1 = new TechStack(techStackConverter.convertStringToTech(techStackList.get(index3)).get(j), user);
//                techStacks.add(techStack1);
//                techStackRepository.save(techStack1);
//            }
//            user.addTechStackList(techStacks);
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
//        online.add("?????????????????? ");
//        online.add("?????????????????? ");
//        online.add("????????? ??? ???????????? ");
//        online.add("??????????????? ");
//        online.add("????????????????????? ???????????? ");
//
//        term.add("?????? ");
//        term.add("3????????? ");
//        term.add("6????????? ");
//        term.add("1?????? ");
//        term.add("???????????? ");
//
//        subject.add("???????????? ");
//        subject.add("????????? ");
//        subject.add("?????? ");
//        subject.add("????????? ");
//        subject.add("????????? ");
//        subject.add("iOS ");
//        subject.add("??????????????? ");
//        subject.add("?????? ");
//        subject.add("??????????????? ");
//        subject.add("????????? ");
//
//        person.add("????????? ");
//        person.add("?????? ???????????? ??? ");
//        person.add("?????? ?????? ??? ");
//        person.add("???????????? ");
//        person.add("????????? ");
//        person.add("????????? ");
//        person.add("?????? ");
//        person.add("????????? ????????? ");
//
//        want.add("?????????.");
//        want.add("?????????.");
//        want.add("????????????.");
//        want.add("????????????.");
//        want.add("?????????.");
//
//        member.add(6);
//        member.add(5);
//        member.add(4);
//        member.add(3);
//        member.add(2);
//
////        List<PostRequestDto> requestDtoList = new ArrayList<>();
//        for (String on : online) {
//            for (String ter : term) {
//                for (String sub : subject) {
//                    for (String per : person) {
//                        for (String wan : want) {
//                            index1 = (int) ((Math.random() * 100) % member.size());
//                            index2 = (int) ((Math.random() * 100) % techStackList.size());
//                            index3 = (int) ((Math.random() * 100) % userList.size());
//                            String title = on + ter + sub + per + wan;
//                            int totalMember = member.get(index1);
//                            String contents = on + ter + sub + per + totalMember + "??? " + wan;
//                            int period1 = (int) ((Math.random() * 100) % 20);
//                            int period2 = (int) (Math.random() * 100);
//
//                            Timestamp startDate = Timestamp.valueOf(LocalDate.now().plusDays(1).plusDays(period1).atStartOfDay());
//                            Timestamp endDate = Timestamp.valueOf(startDate.toLocalDateTime().plusDays(1).plusDays(period2));
//                            PostRequestDto postRequestDto = new PostRequestDto(
//                                    title,
//                                    contents,
//                                    totalMember,
//                                    "?????????",
//                                    startDate,
//                                    endDate,
//                                    techStackList.get(index2),
//                                    "http://test.url"
//                            );
////                        requestDtoList.add(postRequestDto);
//
//                            postService.writePost(postRequestDto, userList.get(index3).getSnsId());
//                        }
//                    }
//                }
//            }
//        }
//
//        /*
//        ?????? ????????? 100??? ??????
//         */
////        List<String> postTechStack = new ArrayList<>();
////        postTechStack.add("Java");
////        postTechStack.add("JavaScript");
////        postTechStack.add("Python");
////        postTechStack.add("Node");
////        postTechStack.add("cpp");
////        postTechStack.add("Flask");
////        postTechStack.add("Django");
////        postTechStack.add("React");
////        postTechStack.add("php");
////        postTechStack.add("Swift");
////        postTechStack.add("Kotlin");
////        postTechStack.add("TypeScript");
////        postTechStack.add("Spring");
////        List<String> online = new ArrayList<>();
////        List<String> term = new ArrayList<>();
////        List<String> subject = new ArrayList<>();
////        List<String> person = new ArrayList<>();
////        List<String> want = new ArrayList<>();
////        List<Integer> member = new ArrayList<>();
////
////        online.add("?????????????????? ");
////        online.add("?????????????????? ");
////        online.add("????????? ??? ???????????? ");
////        online.add("??????????????? ");
////        online.add("????????????????????? ???????????? ");
////
////        term.add("?????? ");
////        term.add("3????????? ");
////        term.add("6????????? ");
////        term.add("1?????? ");
////        term.add("???????????? ");
////
////        subject.add("???????????? ");
////        subject.add("????????? ");
////        subject.add("?????? ");
////        subject.add("????????? ");
////        subject.add("????????? ");
////        subject.add("iOS ");
////        subject.add("??????????????? ");
////        subject.add("?????? ");
////        subject.add("??????????????? ");
////        subject.add("????????? ");
////
////        person.add("????????? ");
////        person.add("?????? ???????????? ??? ");
////        person.add("?????? ?????? ??? ");
////        person.add("???????????? ");
////        person.add("????????? ");
////        person.add("????????? ");
////        person.add("?????? ");
////        person.add("????????? ????????? ");
////
////        want.add("?????????.");
////        want.add("?????????.");
////        want.add("????????????.");
////        want.add("????????????.");
////        want.add("?????????.");
////
////        member.add(6);
////        member.add(5);
////        member.add(4);
////        member.add(3);
////        member.add(2);
////        List<User> curUserList = userRepository.findAll();
////        for (int i = 0; i < 100; i++) {
////            String on = online.get(getRandomIndex(online.size()));
////            String ter = term.get(getRandomIndex(term.size()));
////            String sub = subject.get(getRandomIndex(subject.size()));
////            String per = person.get(getRandomIndex(person.size()));
////            String wan = want.get(getRandomIndex(want.size()));
////            String title = on + ter + sub + per + wan;
////            int totalMember = member.get(getRandomIndex(member.size()));
////            String contents = on + ter + sub + per + totalMember + "??? " + wan;
////            Timestamp startDate = Timestamp.valueOf(LocalDate.now().plusDays(getRandomIndex(20)).atStartOfDay());
////            Timestamp endDate = Timestamp.valueOf(startDate.toLocalDateTime().plusDays(1 + getRandomIndex(100)));
////            Set<String> randomTechStack = new HashSet<>();
////            int techCount = getRandomIndex(4);
////            while(randomTechStack.size() < techCount){
////                randomTechStack.add(postTechStack.get(getRandomIndex(postTechStack.size())));
////            }
////            PostRequestDto postRequestDto = new PostRequestDto(
////                    title,
////                    contents,
////                    totalMember,
////                    "?????????",
////                    startDate,
////                    endDate,
////                    new ArrayList<>(randomTechStack),
////                    "url"
////            );
////
////            postService.writePost(postRequestDto, curUserList.get(getRandomIndex(curUserList.size())).getSnsId());
////        }
////    }
////
////    private int getRandomIndex(int size) {
////        return (int) ((Math.random() * 100) % size);
////    }
//    }
//}
