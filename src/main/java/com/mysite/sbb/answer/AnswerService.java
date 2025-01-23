package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionDto;
import com.mysite.sbb.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;


    /*public void create(Question question, String content) {
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setCreateDate(LocalDateTime.now());
        answer.setQuestion(question);
        this.answerRepository.save(answer);
    }
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

    */

    /*

        답변 내용을 저장할 때 글쓴이 데이터도 저장 : 객체로 UserDto 추가

    */
    public void create(QuestionDto questionDto, String content, UserDto userDto) {
        //AnswerMapper mapper = AnswerMapper.INSTANCE;

        // QuestionDto를 Question 엔티티로 변환
        AnswerDto answerDto = AnswerDto.builder()
                .content(content)
                .createDate(LocalDateTime.now())
                .question(questionDto)
                .author(userDto)
                .build();

        // DTO를 엔티티로 변환
        Answer answer = answerMapper.toEntity(answerDto);

        this.answerRepository.save(answer);
    }
}