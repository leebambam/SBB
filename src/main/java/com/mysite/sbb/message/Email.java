package com.mysite.sbb.message;

import com.mysite.sbb.user.SiteUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_mail_log")
public class Email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mail_sn;

    // 메일 발신 주소
    @Column(nullable = false, length = 50)
    private String sender;

    // 메일 수신 주소
    @Column(nullable = false, length = 50)
    private String recipient;

    // 이메일 제목
    @Column(nullable = false, length = 100)
    private String subject;

    // 이메일 내용
    @Lob //  대용량 데이터(문자열 또는 바이너리)를 저장할 수 있도록 JPA가 자동으로 처리
    @Column(nullable = false, columnDefinition = "MEDIUMTEXT")
    private String content;

    // 발송 처리 상태
    @Column(nullable = false, length = 1)
    private String status;

    // 발송 일시
    @Column(name = "send_dt", columnDefinition = "DATETIME(0)")
    private LocalDateTime send_dt;

    // 등록 아이디
    @ManyToOne
    private SiteUser reg_id;

}
