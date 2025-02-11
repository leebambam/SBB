package com.mysite.sbb.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ChangePasswordDto {
    @NotBlank(message = "현재 비밀번호를 입력하세요.")
    private String oldPassword;

    @NotBlank(message = "새 비밀번호를 입력하세요.")
    //@Size(min = 8, message = "새 비밀번호는 최소 8자 이상이어야 합니다.")
    private String newPassword;

    @NotBlank(message = "새 비밀번호 확인을 입력하세요.")
    private String confirmPassword; // 새 비밀번호 확인
}
