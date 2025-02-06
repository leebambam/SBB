package com.mysite.sbb.comment;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerDto;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionDto;
import com.mysite.sbb.user.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentDto {
    private Integer id;
    private String content;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private UserDto author;
    private AnswerDto answer;

}
