package com.mysite.sbb.message;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Getter
@Setter
public class EmailForm {

    @NotEmpty(message="발송 내용은 필수항목입니다.")
    private String content;

    @NotEmpty(message="제목은 필수항목입니다.")
    private String subject;

}
