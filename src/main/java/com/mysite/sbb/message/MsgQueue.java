package com.mysite.sbb.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "msg_queue")  // 실제 테이블명 지정
public class MsgQueue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mseq;

    private String dstaddr; // 회신 번호

    private String callback; // 발신 번호

    private String text; // 메세지 내용

    private String custid; // 사용자 아이디

    private LocalDateTime request_time;

}
