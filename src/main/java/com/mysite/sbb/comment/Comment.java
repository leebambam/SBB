package com.mysite.sbb.comment;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private LocalDateTime createDate;

    // 댓글 작성자
    @ManyToOne
    @JoinColumn(name = "author_id")
    private SiteUser author;

    // 질문에 달린 댓글일 경우
    @ManyToOne
    @JoinColumn(name = "question_id", nullable = true)
    private Question question;

    // 답변에 달린 댓글일 경우
    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = true)
    private Answer answer;

    @Builder
    public Comment(String content, LocalDateTime createDate, SiteUser author, Question question, Answer answer) {
        this.content = content;
        this.createDate = createDate;
        this.author = author;
        this.question = question;
        this.answer = answer;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
