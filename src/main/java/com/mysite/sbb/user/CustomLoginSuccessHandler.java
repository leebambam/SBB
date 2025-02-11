package com.mysite.sbb.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
/*
    CustomLoginSuccessHandler : Spring Security의 로그인 성공 이벤트를 처리하는 커스텀 로그인 성공 핸들러
    implements AuthenticationSuccessHandler : Spring Security에서 제공하는 로그인 성공 후 처리하는 인터페이스
                                , onAuthenticationSuccess() 메서드를 오버라이드하여 로그인 성공 후 로직을 정의

*/
@Component // Spring Bean으로 등록(Spring이 이 클래스를 자동으로 관리하도록 지정)
@Slf4j
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    public CustomLoginSuccessHandler(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /*
        onAuthenticationSuccess: 로그인 성공 시 자동 실행되는 메서드

        * 매개변수 설명
        HttpServletRequest request: HTTP 요청 객체
        HttpServletResponse response: HTTP 응답 객체
        Authentication authentication: 로그인한 사용자의 인증 정보

        authentication.getPrincipal() : 로그인한 사용자 정보(UserDetails) 가져오기
        UserDetails : Spring Security에서 사용자의 정보를 담는 기본 인터페이스

    */
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<SiteUser> optionalUser = userRepository.findByusername(userDetails.getUsername());

        if (optionalUser.isPresent()) {
            SiteUser siteUser = optionalUser.get();
            log.info("User {} logged in successfully", siteUser.getUsername());
            // 임시 비밀번호 사용자라면 비밀번호 변경 페이지로 리다이렉트
            if (siteUser.isTempPassword()) {
                response.sendRedirect("/user/change_password");
            } else {
                response.sendRedirect("/"); // 기본적으로 홈 페이지로 이동
            }
        } else {
            response.sendRedirect("/");
            log.warn("User not found after successful authentication: {}", userDetails.getUsername());
        }
    }
}
