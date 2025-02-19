package com.mysite.sbb.message;

import com.mysite.sbb.user.UserDto;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/message")
@RequiredArgsConstructor
@Controller
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    @GetMapping("/sms")
    //@ResponseBody // URL 요청에 대한 응답으로 문자열을 리턴하라는 의미
    public String sms(Model model){

        List<UserDto> userDto = messageService.getUser();

        model.addAttribute("user", userDto);

        return "sms_form";
    }
}
