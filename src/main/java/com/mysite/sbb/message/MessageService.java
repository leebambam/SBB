package com.mysite.sbb.message;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserDto;
import com.mysite.sbb.user.UserMapper;
import com.mysite.sbb.user.UserRepository;
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
                                .request_time(LocalDateTime.now())
                                .build())
                .collect(Collectors.toList());

        List<MsgQueue> msgQueue = msgQueueMapper.toEntityList(msgQueueDtoList);

        this.msgQueueRepository.saveAll(msgQueue);
    }
}
