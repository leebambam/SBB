package com.mysite.sbb.answer;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.question.QuestionDto;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserDto;
import com.mysite.sbb.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;
    private final UserMapper userMapper;


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


    /*public Answer getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }*/
    // 답변 조회
    public AnswerDto getAnswer(Integer id) {
        Optional<Answer> answer = this.answerRepository.findById(id);
        if (answer.isPresent()) {
            //return answer.get();
            return answerMapper.toDto(answer.get());
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    // 답변 수정
    public void modify(AnswerDto answerDto, String content) {
        answerDto = answerDto.toBuilder()
                .content(content)
                .modifyDate(LocalDateTime.now())
                .build();

        Answer answer = answerMapper.toEntity(answerDto);

        this.answerRepository.save(answer);
    }

    // 답변 삭제
    public void delete(AnswerDto answerDto) {

        Answer answer = answerMapper.toEntity(answerDto);

        this.answerRepository.delete(answer);
    }

    // 담변 추천한 사람 저장
    public void vote(AnswerDto answerDto, UserDto userDto){
        Answer answer = answerMapper.toEntity(answerDto);
        SiteUser siteUser = userMapper.toEntity(userDto);

        answer.getVoter().add(siteUser);

        this.answerRepository.save(answer);
    }
}