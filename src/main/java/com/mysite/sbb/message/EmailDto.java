package com.mysite.sbb.message;


import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder(toBuilder = true)
public class EmailDto {
    private Integer mail_sn;
    private String sender;
    private String recipient;
    private String subject;
    private String content;
    private String status;
    private LocalDateTime send_dt;
    private UserDto reg_id;

}
