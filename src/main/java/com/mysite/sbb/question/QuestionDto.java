package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class QuestionDto {
    private Integer id;
    private String subject;
    private String content;
    private LocalDateTime createDate;
    private List<Answer> answerList;

    // 엔티티를 DTO로 변환
    public static QuestionDto fromEntity(Question question) {
        return QuestionDto.builder()
                .id(question.getId())
                .subject(question.getSubject())
                .content(question.getContent())
                .createDate(question.getCreateDate())
                .answerList(question.getAnswerList())
                .build();
    }

    // DTO를 엔티티로 변환
    public Question toEntity() {
        return Question.builder()
                .id(this.id)
                .subject(this.subject)
                .content(this.content)
                .createDate(this.createDate)
                .answerList(this.answerList)
                .build();
    }
}
