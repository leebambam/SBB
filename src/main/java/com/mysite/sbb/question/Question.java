package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.mysite.sbb.answer.Answer;

import lombok.*;

@Getter
//@Setter
@Builder
@NoArgsConstructor // 기본 생성자 (파라미터가 없는 생성자)를 생성, 객체 초기화를 위해 기본 생성자를 명시적으로 작성하지 않아도 자동으로 생성됩니다.
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자를 생성합니다.
@Entity // 엔티티란? 데이터베이스 테이블과 매핑되는 자바 클래스
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL) // 질문 하나에 답변은 여러 개
    private List<Answer> answerList;
}