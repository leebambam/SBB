package com.mysite.sbb.user;


import com.mysite.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Optional;

@RequiredArgsConstructor // final 필드나 @NonNull이 붙은 필드를 초기화하는 생성자를 자동으로 생성
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    // private static final Logger logger = LoggerFactory.getLogger(UserController.class); // Logger 생성
    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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

    /*
    /user/login URL로 들어오는 GET 요청을 이 메서드가 처리

    실제 로그인을 진행하는 @PostMapping 방식의 메서드는
    스프링 시큐리티가 대신 처리하므로 우리가 직접 코드를 작성하여 구현할 필요가 없다.
    */
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }

    @GetMapping("/find_password")
    public String findPassword(FindPasswordDto findPasswordDto) {
        return "find_password";
    }

    // 비밀번호 찾기
    @PostMapping("/find_password")
    public String processFindPassword(@Valid FindPasswordDto findPasswordDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "find_password";
        }
        try {
            userService.sendTemporaryPassword(findPasswordDto);
        } catch (DataNotFoundException e) { // 이메일이 존재하지 않는 경우
            log.warn("비밀번호 찾기 실패: 존재하지 않는 사용자 ID 또는 이메일 - ID: {}, Email: {}",
                    findPasswordDto.getUsername(), findPasswordDto.getEmail());
            bindingResult.reject("findPasswordFailed", "사용자 ID 또는 이메일이 일치하지 않습니다.");
            return "find_password";
        } catch (Exception e) { // 기타 예상치 못한 예외 처리
            log.error("비밀번호 찾기 중 오류 발생", e);
            bindingResult.reject("findPasswordFailed", "비밀번호 찾기 중 오류가 발생했습니다. 다시 시도해주세요.");
            return "find_password";
        }

        return "redirect:/user/login?resetSuccess";  // 성공 시 로그인 페이지로 이동
    }

    // 비밀번호 변경 페이지
    @GetMapping("/change_password")
    public String showChangePasswordForm(ChangePasswordDto changePasswordDto) {
        return "change_password";
    }

    // 비밀번호 변경
    @PostMapping("/change_password")
    public String changePassword(ChangePasswordDto changePasswordDto, BindingResult bindingResult,
                                    @AuthenticationPrincipal UserDetails userDetails){
            //,RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "change_password";
        }

        String username = userDetails.getUsername();
        UserDto userDto = userService.findByUsername(username);


        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(changePasswordDto.getOldPassword(), userDto.getPassword())) {
            bindingResult.rejectValue("oldPassword", "invalid", "현재 비밀번호가 일치하지 않습니다.");
            return "change_password";
        }

        // 새 비밀번호 확인
        if (!changePasswordDto.getNewPassword().equals(changePasswordDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "mismatch", "새 비밀번호가 일치하지 않습니다.");
            return "change_password";
            //return "redirect:/user/change_password"; 리디렉트 시 BindingResult의 에러 메시지가 유지되지 않음.
        }

        // 비밀번호 변경 및 임시 비밀번호 해제
        userService.updatePassword(userDto, changePasswordDto.getNewPassword());

        return "redirect:/user/login";
    }
}
