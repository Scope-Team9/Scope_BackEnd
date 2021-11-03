package com.studycollaboproject.scope.service;

import com.studycollaboproject.scope.dto.MailDto;
import com.studycollaboproject.scope.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@RequiredArgsConstructor
@Service
public class MailService {

    private final JavaMailSender javaMailSender;

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

    public void setMail(String sub, String body, String email) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "UTF-8");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setSubject(sub);
        mimeMessageHelper.setText(body, true);
        sendMail(mimeMessageHelper.getMimeMessage());
    }

    public void applicantMAilBilder(MailDto mailDto) throws MessagingException {
        String subject = "[scope]"+mailDto.getFromNickname()+"님이 팀원 신청을 했습니다.";

        String body = "<html> <body><h1>안녕하세요 "+mailDto.getToNickname()+"님!</h1>" +
                "<div>"+mailDto.getFromNickname()+"님이 팀원 신청을 했습니다! </div> " +
                "</body></html>";
        String email = mailDto.getToEmail();

        setMail(subject,body,email);
    }

    public void sendMail(MimeMessage message) {
        getJavaMailSender().send(message);

    }

    public void acceptTeamMailBilder(MailDto mailDto) throws MessagingException {
        String subject = "[scope]"+mailDto.getFromNickname()+"님이 프로젝트 신청을 수락했습니다!";

        String body = "<html> <body><h1>안녕하세요 "+mailDto.getToNickname()+"님!</h1>" +
                "<div>"+mailDto.getFromNickname()+"님이 프로젝트 신청을 수락했습니다!</div> " +
                "</body></html>";

        String email = mailDto.getToEmail();

        setMail(subject,body,email);


    }

    public void assessmantMailBilder(MailDto mailDto) throws MessagingException {
        for (User user : mailDto.getToUserList()) {
            String subject = "[scope]"+user.getNickname()+"님의 프로젝트가 종료되었습니다!";

            String body = "<html> <body><h1>안녕하세요 "+user.getNickname()+"님!</h1>" +
                    "<div>프로젝트가 종료되었습니다!<br>협업했던 팀원들은 어떠셨나요?<br>잘 맞았던 팀원을 추천해주세요!(결과는 저희만 알고있을게요)</div> " +
                    "</body></html>";

            String email = user.getEmail();

            setMail(subject,body,email);
        }

    }
}
