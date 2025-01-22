package com.mysite.sbb.user;

import lombok.Getter;

/*
    - 스프링 시큐리티는 사용자 인증 후에 사용자에게 부여할 권한과 관련된 내용이 필요하다.

    - enum 자료형(열거 자료형) : 상수값(고정된 값)을 정의하기 위해 사용됨

    - ADMIN과 USER 상수는 값을 변경할 필요가 없으므로 @Setter 없이 @Getter만 사용할 수 있도록 했다.

    ** ADMIN 권한(관리자 권한)을 지닌 사용자가 다른 사람이 작성한 질문이나 답변을 수정 가능하도록 만들어 보기?

*/
@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    /*
    Enum 상수(ADMIN, USER)가 생성될 때,
    각각 "ROLE_ADMIN"과 "ROLE_USER" 값을 생성자에 전달

    생성자는 전달받은 값을 value 필드에 저장
    @Getter로 인해 value 필드를 읽을 수 있는 메서드 getValue()가 자동 생성

    */

    // Enum에 전달된 값을 받아 value 필드에 할당하는 생성자
    UserRole(String value) {
        this.value = value;
    }

    private String value;
}