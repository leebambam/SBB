package com.mysite.sbb.answer;

import com.mysite.sbb.comment.Comment;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Getter
//@Setter
@NoArgsConstructor
// 기본 생성자(매개변수가 없는 생성자)를 자동으로 생성, 기본 생성자의 접근 수준을 protected로 설정
// @NoArgsConstructor(access = AccessLevel.PROTECTED) : 즉, 이 클래스는 외부에서 인스턴스를 직접 생성할 수 없고, 상속 받은 클래스에서만 기본 생성자를 호출할 수 있습니다.
@AllArgsConstructor // 모든 필드를 매개변수로 받는 생성자를 생성합니다.
@Entity
@Builder
/*
*  빌더 클래스를 자동 생성
* Answer answer = Answer.builder()
    .content(content)
    .createDate(LocalDateTime.now())
    .question(question)
    .build();
*
* */
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    private LocalDateTime modifyDate;

    @ManyToMany
    Set<SiteUser> voter;

    @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
    private List<Comment> commentList;


    // 향후 사용 가능한지 체크~
    public Answer(String content, LocalDateTime createDate, Question question) {
        this.content = content;
        this.createDate = createDate;
        this.question = question;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}