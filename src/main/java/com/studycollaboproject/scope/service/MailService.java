package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.MailDto;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.BadRequestException;
import com.studycollaboproject.scope.model.User;
import com.studycollaboproject.scope.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

@RequiredArgsConstructor
@Service
@Slf4j
public class MailService {

    private final SpringTemplateEngine templateEngine;
    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;
    private static final Map<String, String> propensityMatching = new HashMap<String, String>() {
        {
            put("LVG", "호랑이");
            put("LVP", "늑대");
            put("LHG", "여우");
            put("LHP", "곰");
            put("FVG", "토끼");
            put("FVP", "허스키");
            put("FHG", "고양이");
            put("FHP", "물개");
            put("RHP", "너구리");
        }
    };

    @Value("${serverUrl}")
    private String url;
    @Value("${spring.mail.password}")
    private String pass;
    @Value("${spring.mail.username}")
    private String username;

    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(pass);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }

    public void setMail(String subject, String body, String email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body, true);
        sendMail(mimeMessageHelper.getMimeMessage());
    }

    @Async
    public void applicantMailBuilder(MailDto mailDto) throws MessagingException {

        log.info("==================================================");
        log.info("지원 알림 메일 발송");
        String email = mailDto.getToEmail();
        Context context = new Context();
        context.setVariable("logo", url + "/img/logo.png");
        context.setVariable("comment", mailDto.getComment());
        context.setVariable("postTitle", mailDto.getPostTitle());
        context.setVariable("toNickname", mailDto.getToNickname());
        context.setVariable("fromNickname", mailDto.getFromNickname());
        context.setVariable("postId", mailDto.getPostId());
        context.setVariable("userId", mailDto.getToUserId());
        String subject = "[scope]" + mailDto.getToNickname() + "님의 프로젝트에" + mailDto.getFromNickname() + "님이 팀원 신청을 했습니다.";
        String body = templateEngine.process("applicantEmail", context);
        setMail(subject, body, email);
        log.info("==================================================");
    }

    public void sendMail(MimeMessage message) {
        getJavaMailSender().send(message);
    }

    @Async
    public void acceptTeamMailBuilder(MailDto mailDto) throws MessagingException {

        log.info("==================================================");
        log.info("프로젝트 매칭 알림 메일 발송");

        String email = mailDto.getToEmail();
        Context context = new Context();
        context.setVariable("logo", url + "/img/logo.png");
        context.setVariable("title", mailDto.getPostTitle());
        context.setVariable("toNickname", mailDto.getToNickname());
        context.setVariable("fromNicckname", mailDto.getFromNickname());
        context.setVariable("postId", mailDto.getPostId());
        String subject = "[scope]" + mailDto.getToNickname() + "님의 프로젝트 신청이 수락되었습니다!";
        String body = templateEngine.process("acceptTeamEmail", context);

        setMail(subject, body, email);
        log.info("==================================================");
    }

    @Async
    public void assessmentMailBuilder(MailDto mailDto) throws MessagingException {

        log.info("==================================================");
        log.info("프로젝트 종료 알림 메일 발송");


        for (User user : mailDto.getToUserList()) {
            Context context = new Context();
            context.setVariable("logo", url + "/img/logo.png");
            context.setVariable("title", mailDto.getPostTitle());
            context.setVariable("nickname", user.getNickname());
            context.setVariable("userId", mailDto.getToUserId());
            String subject = "[scope]" + user.getNickname() + "님의 프로젝트가 종료되었습니다!";
            String body = templateEngine.process("applicationNoticeEmail", context);
            setMail(subject, body, user.getEmail());
            log.info("==================================================");
        }
    }

    @Async
    @Transactional
    public void authMailSender(String email, User user) throws MessagingException {
        log.info("==================================================");
        log.info("이메일 인증 메일 발송");
        Context context = new Context();
        context.setVariable("logo", url + "/img/logo.png");
        context.setVariable("propensityType", url + "/img/" + propensityMatching.get(user.getUserPropensityType()) + ".png");
        context.setVariable("userId", user.getId());
        context.setVariable("code", user.getMailAuthenticationCode());
        context.setVariable("nickname", user.getNickname());
        String subject = "[scope]" + user.getNickname() + "님! 이메일 인증을 완료해주세요.";
        String body = templateEngine.process("emailAuthentication", context);
        setMail(subject, body, email);
    }

    public String emailAuthCodeCheck(String code, Long userId) {

        Optional<User> user = userRepository.findById(userId);


        if (user.isPresent()) {
            if (user.get().getMailAuthenticationCode().equals(code)) {
                user.get().verifiedEmail();
                return "이메일 인증이 완료되었습니다.";
            } else {
                return "유효한 토큰값이 아닙니다.";
            }
        } else {
            return "유저를 찾을 수 없습니다.";
        }
    }
}
