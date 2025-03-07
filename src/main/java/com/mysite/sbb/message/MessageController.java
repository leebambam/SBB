package com.mysite.sbb.message;

import com.mysite.sbb.user.UserDto;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/message")
@RequiredArgsConstructor
@Controller
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/sms")
    //@ResponseBody // URL 요청에 대한 응답으로 문자열을 리턴하라는 의미
    public String sms(Model model, SmsForm smsForm){

        List<UserDto> userDto = messageService.getUser();

        model.addAttribute("user", userDto);

        return "sms_form";
    }

    @PostMapping("/sms")
    public String sms(@Valid SmsForm smsForm, @RequestParam("selectedNumbers") String selectedNumbers,
                        BindingResult bindingResult, Principal principal){
        if (bindingResult.hasErrors()) {
            return "sms_form";
        }

        // 즉시 발송이면 현재 시간 설정
        if ("immediate".equals(smsForm.getSendType())) {
            smsForm.setSendTime(LocalDateTime.now());
        }

        //SmsDto smsDto = new SmsDto( principal.getName(), smsForm.getMessageContent(), smsForm.getSenderPhoneNumber());
        SmsDto smsDto = SmsDto.builder()
                .messageContent(smsForm.getMessageContent())
                .senderPhoneNumber(smsForm.getSenderPhoneNumber())
                .sendTime(smsForm.getSendTime())
                .build();

        UserDto userDto = this.userService.getUser(principal.getName());

        // 선택된 번호들을 리스트로 변환
        List<String> phoneNumbers = Arrays.asList(selectedNumbers.split(","));

        this.messageService.smsSend(smsDto, userDto, phoneNumbers);
        return "redirect:/question/list";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/email")
    public String email(Model model, EmailForm emailForm){

        List<UserDto> userDto = messageService.getUser();

        model.addAttribute("user", userDto);

        return "email_form";
    }


    @PostMapping("/email")
    public String email(@Valid EmailForm emailForm, @RequestParam("selectedEmails") String selectedEmails,
                        BindingResult bindingResult, Principal principal, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "email_form";
        }

        EmailDto emailDto = EmailDto.builder()
                .sender("nheene30@gemtek.co.kr")
                .subject(emailForm.getSubject())
                .content(emailForm.getContent())
                .send_dt(LocalDateTime.now())
                .build();

        UserDto userDto = this.userService.getUser(principal.getName());

        // 선택된 번호들을 리스트로 변환
        List<String> emails = Arrays.asList(selectedEmails.split(","));

        this.messageService.emailSend(emailDto, userDto, emails);

        // 📌 성공 메시지 추가
        redirectAttributes.addFlashAttribute("emailSuccess", true);

        return "redirect:/question/list";
    }

}
