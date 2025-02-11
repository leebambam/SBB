package com.mysite.sbb.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SiteUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    @Builder.Default
    //임시 비밀번호 여부 확인 (true: 임시 비밀번호 사용 중)
    private boolean tempPassword = false; // 기본값 false
}
