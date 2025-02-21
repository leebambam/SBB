package com.mysite.sbb.message;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class SmsForm {
    @NotEmpty(message="발송 내용은 필수항목입니다.")
    private String messageContent;

    @NotEmpty(message="회신번호는 필수항목입니다.")
    private String senderPhoneNumber;

    //private LocalDateTime sendTime;
}
