package com.mysite.sbb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Async
    // 별도의 쓰레드(Thread)에서 메소드를 실행하여, 메인 쓰레드가 블로킹되지 않고 바로 다음 작업을 수행할 수 있다.
    // 여러 개의 쓰레드가 병렬로 실행되면서 동시에 여러 메일을 보낼 수 있음.
    public void sendEmail(String to, String subject, String text) {
        try {
            //  MimeMessage : HTML 및 첨부파일 지원 가능
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            helper.setFrom("nheene30@gemtek.co.kr");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패", e);
        }
    }

    /*public void sendEmail(List<String> recipients, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");

            helper.setTo(recipients.toArray(new String[0]));  // 다중 이메일 전송
            helper.setSubject(subject);
            helper.setText(text);
            helper.setFrom("nheene30@gemtek.co.kr");

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 실패", e);
        }
    }*/
}
