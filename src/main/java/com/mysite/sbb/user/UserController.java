package com.mysite.sbb.user;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequiredArgsConstructor // final 필드나 @NonNull이 붙은 필드를 초기화하는 생성자를 자동으로 생성
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    // private static final Logger logger = LoggerFactory.getLogger(UserController.class); // Logger 생성
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "signup_form";
        }

        if(!userCreateForm.getPassword1().equals((userCreateForm.getPassword2()))){
            bindingResult.rejectValue("password2","passwordInCorrect","2개의 패스워드가 일치하지 않습니다.");
            return "signup_form";
        }

        try{
            // UserCreateForm → UserDto 변환
            UserDto userDto = userMapper.formToDto(userCreateForm);
            userService.create(userDto);
        }catch(DataIntegrityViolationException e){ // 사용자 ID 또는 이메일 주소가 이미 존재할 경우
            //e.printStackTrace();
            // logger.error("회원가입 실패: 이미 등록된 사용자", e);
            log.info("회원가입 실패: 이미 등록된 사용자", e);
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        }catch(Exception e) {
            // e.printStackTrace();
            // logger.error("회원가입 중 알 수 없는 오류 발생", e);
            log.warn("회원가입 중 알 수 없는 오류 발생", e);
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }


        return "redirect:/";
    }
}
