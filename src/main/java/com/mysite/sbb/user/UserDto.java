package com.mysite.sbb.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
}
