package com.mysite.sbb.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean tempPassword;
}
