package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;


    /*public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }*/
    public void create(QuestionDto questionDto, String content) {
        Question question = Question.builder()
                .id(questionDto.getId())
                .subject(questionDto.getSubject())
                .content(questionDto.getContent())
                .createDate(questionDto.getCreateDate())
                .build();

        Answer answer = Answer.builder()
                .content(content)
                .createDate(LocalDateTime.now())
                .question(question)
                .build();

        this.answerRepository.save(answer);
    }
}