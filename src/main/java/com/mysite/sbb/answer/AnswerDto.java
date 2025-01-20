package com.mysite.sbb.answer;

import com.mysite.sbb.question.QuestionDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AnswerDto {
    private Integer id;
    private String content;
    private LocalDateTime createDate;
    private QuestionDto question;


    /*

    // 엔티티를 DTO로 변환
    public static AnswerDto fromEntity(Answer answer) {
        return AnswerDto.builder()
                .id(answer.getId())
                .content(answer.getContent())
                .createDate(answer.getCreateDate())
                .question(QuestionDto.fromEntity(answer.getQuestion()))
                .build();
    }

    // DTO를 엔티티로 변환
    public Answer toEntity() {
        return Answer.builder()
                .id(this.id)
                .content(this.content)
                .createDate(this.createDate)
                .question(this.question.toEntity()) // QuestionDto를 다시 엔티티로 변환
                .build();
    }

    */
}
