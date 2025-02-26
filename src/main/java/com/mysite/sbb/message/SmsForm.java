package com.mysite.sbb.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class SmsForm {
    @NotEmpty(message="발송 내용은 필수항목입니다.")
    private String messageContent;

    @NotEmpty(message="회신번호는 필수항목입니다.")
    private String senderPhoneNumber;

    private String sendType;  // 즉시/예약 발송 구분

    /*
        sendTime 필드가 LocalDateTime 타입인데,
        클라이언트에서 "2025-02-26T15:06" 형식으로 전송되었기 때문
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") // HTML의 datetime-local 형식 처리
    private LocalDateTime sendTime;
}
