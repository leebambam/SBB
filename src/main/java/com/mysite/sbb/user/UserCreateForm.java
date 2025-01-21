package com.mysite.sbb.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
// 사용자로부터 입력받은 값을 검증하는데 필요한 폼 클래스
public class UserCreateForm {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "사용자ID는 필수항목입니다.")
    private String username;

    @NotEmpty(message = "비밀번호는 필수항목입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수항목입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.") // 유효한 이메일 주소인지 검증
    private String email;
}
