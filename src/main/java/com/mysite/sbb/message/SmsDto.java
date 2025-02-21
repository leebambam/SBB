package com.mysite.sbb.message;

import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
public class SmsDto {
    private Integer id;
    private UserDto user;
    private String messageContent;
    private String senderPhoneNumber;
    //private LocalDateTime sendTime;
}
