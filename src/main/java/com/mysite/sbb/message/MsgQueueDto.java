package com.mysite.sbb.message;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class MsgQueueDto {
    private Long mseq;
    private String dstaddr;
    private String callback;
    private String text;
    private String custid;
    private LocalDateTime request_time;
}
