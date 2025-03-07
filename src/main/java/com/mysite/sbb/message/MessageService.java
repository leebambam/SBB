package com.mysite.sbb.message;

import com.mysite.sbb.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final MsgQueueRepository msgQueueRepository;
    private final MsgQueueMapper msgQueueMapper;
    private final EmailMapper emailMapper;
    private final EmailRepository emailRepository;
    private final EmailService emailService;

    public List<UserDto> getUser() {
        List<SiteUser> siteUser = this.userRepository.findAll();

        return userMapper.toDtoList(siteUser);
    }

    public void smsSend(SmsDto smsDto, UserDto userDto, List<String> phoneNumbers) {

        if (msgQueueMapper == null) {
            throw new IllegalStateException("msgQueueMapper is null!");
        }
        // SMS 공지 히스토리 테이블 등록하기

        // SMS 공지 발송테이블(msg_queue) 등록하기
        List<MsgQueueDto> msgQueueDtoList  = phoneNumbers.stream()
                        .map(phoneNumber -> MsgQueueDto.builder()
                                .dstaddr(phoneNumber)
                                .callback(smsDto.getSenderPhoneNumber())
                                .text(smsDto.getMessageContent())
                                .custid(userDto.getUsername())
                                .request_time(smsDto.getSendTime())
                                .build())
                .collect(Collectors.toList());

        List<MsgQueue> msgQueue = msgQueueMapper.toEntityList(msgQueueDtoList);

        this.msgQueueRepository.saveAll(msgQueue);
    }


    public void emailSend(EmailDto emailDto, UserDto userDto, List<String> emails) {

        String subject = emailDto.getSubject();
        String text = emailDto.getContent();

        for (String email : emails) {
            emailService.sendEmail(email, subject, text);
        }

        // E-Mail 공지 발송 Log 테이블(TBL_MAIL_LOG) 등록하기
        List<EmailDto> emailDtoList  = emails.stream()
                .map(emailData -> EmailDto.builder()
                        .sender(emailDto.getSender())
                        .recipient(emailData)
                        .subject(emailDto.getSubject())
                        .content(emailDto.getContent())
                        .status("1")
                        .send_dt(emailDto.getSend_dt())
                        .reg_id(userDto)
                        .build())
                .collect(Collectors.toList());

        List<Email> Email = emailMapper.toEntityList(emailDtoList);

        this.emailRepository.saveAll(Email);
    }
}
