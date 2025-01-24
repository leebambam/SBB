package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
@Builder(toBuilder = true) // toBuilder 활성화
public class QuestionDto {
    private Integer id;
    private String subject;
    private String content;
    private LocalDateTime createDate;
    private List<Answer> answerList;
    private UserDto author;
    private LocalDateTime modifyDate;
    private Set<UserDto> voter;

    /*\
    static : 내부 클래스를 정의하기 위해서

    static으로 선언된 이유는
    Builder 클래스가 UserDTO의 인스턴스 없이도 독립적으로 동작하도록 하기 위함입니다.
    즉, static이 아니었다면 Builder 클래스에서 UserDTO의 인스턴스에 접근하려면
    외부에서 UserDTO 객체를 먼저 생성해야 합니다.



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

    */
}
