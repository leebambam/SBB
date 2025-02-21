package com.mysite.sbb.message;

import com.mysite.sbb.user.UserDto;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/message")
@RequiredArgsConstructor
@Controller
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

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

        //SmsDto smsDto = new SmsDto( principal.getName(), smsForm.getMessageContent(), smsForm.getSenderPhoneNumber());
        SmsDto smsDto = SmsDto.builder()
                .messageContent(smsForm.getMessageContent())
                .senderPhoneNumber(smsForm.getSenderPhoneNumber())
                .build();

        UserDto userDto = this.userService.getUser(principal.getName());

        // 선택된 번호들을 리스트로 변환
        List<String> phoneNumbers = Arrays.asList(selectedNumbers.split(","));

        this.messageService.smsSend(smsDto, userDto, phoneNumbers);
        return "redirect:/question/list";
    }

}
