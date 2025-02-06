package com.mysite.sbb.answer;

import com.mysite.sbb.comment.Comment;
import com.mysite.sbb.comment.CommentDto;
import com.mysite.sbb.question.QuestionDto;
import com.mysite.sbb.user.UserDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Builder(toBuilder = true) // toBuilder 활성화
public class AnswerDto {
    private Integer id;
    private String content;
    private LocalDateTime createDate;
    private QuestionDto question;
    private UserDto author;
    private LocalDateTime modifyDate;
    private Set<UserDto> voter;
    //private int voteCount;
    private List<CommentDto> commentList;
    private Page<AnswerDto> answerPage;

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
