package com.mysite.sbb.message;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Sms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // SMS를 보내는 사용자
    @ManyToOne
    private SiteUser user;

    // 메세지 내용
    @Column(nullable = false)
    private String messageContent;

    // 회신 번호
    @Column(nullable = false)
    private String senderPhoneNumber;
    
    // 발송 시각
    @Column(nullable = false)
    private LocalDateTime sendTime;

}
