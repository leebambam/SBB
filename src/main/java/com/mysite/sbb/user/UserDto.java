package com.mysite.sbb.user;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.message.SmsDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean tempPassword;
    private String phoneNumber;
    //private List<SmsDto> smsList;
}
