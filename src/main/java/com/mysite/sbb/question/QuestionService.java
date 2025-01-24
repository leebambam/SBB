package com.mysite.sbb.question;

import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.UserDto;
import com.mysite.sbb.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final UserMapper userMapper;

    /*public List<Question> getList() {
        return this.questionRepository.findAll(); // Question 엔티티의 모든 데이터를 List<Question> 형태로 반환
    }

    // 아래와 같은 코드
    public List<QuestionDto> getList() {
        List<Question> questions = this.questionRepository.findAll();
        List<QuestionDto> questionDtos = new ArrayList<>();

        for (Question question : questions) {
            QuestionDto dto = QuestionDto.fromEntity(question);
            questionDtos.add(dto);
        }

        return questionDtos;
    }

    public List<QuestionDto> getList() {
        return this.questionRepository.findAll()
                .stream() // .stream()을 사용하여 List<Question>을 스트림으로 변환
                .map(QuestionDto::fromEntity) // Question 객체를 QuestionDto 객체로 변환
                .collect(Collectors.toList()); // map을 통해 변환된 스트림을 다시 List<QuestionDto>로 변환
    }
    */

    public List<QuestionDto> getList() {
        List<Question> questions = this.questionRepository.findAll();
        return questionMapper.toDtoList(questions); // MapStruct로 변환
    }


    /* public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
    */

    public QuestionDto getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
           // return QuestionDto.fromEntity(question.get());
            return questionMapper.toDto(question.get()); // MapStruct로 변환
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    /* public void create(String subject, String content) {
        Question q = new Question();
        q.setSubject(subject);
        q.setContent(content);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);

        public void create(String subject, String content) {
        Question question = Question.builder()
                .subject(subject)
                .content(content)
                .createDate(LocalDateTime.now())
                .build();
        this.questionRepository.save(question);
        }
    }*/
    public void create(QuestionDto questionDto, UserDto userDto) {

        SiteUser author  = this.userMapper.toEntity(userDto);

        Question question = Question.builder()
                .subject(questionDto.getSubject())
                .content(questionDto.getContent())
                .createDate(LocalDateTime.now())
                .author(author)
                .build();
        this.questionRepository.save(question);
    }


    public Page<QuestionDto> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        Page<Question> question = this.questionRepository.findAll(pageable);
        return questionMapper.toDto(question);

    }

    // 질문 수정
    public void modify(QuestionDto questionDto, String subject, String content) {
        questionDto = questionDto.toBuilder()
                .subject(subject)
                .content(content)
                .modifyDate(LocalDateTime.now())
                .build();

        Question question = questionMapper.toEntity(questionDto);

        this.questionRepository.save(question);

    }

    // 질문 삭제
    public void delete(QuestionDto questionDto) {

        Question question = questionMapper.toEntity(questionDto);

        this.questionRepository.delete(question);
    }

    //  로그인한 사용자를 질문 엔티티에 추천인으로 저장
    public void vote(QuestionDto questionDto, UserDto userDto) {

        Question question = questionMapper.toEntity(questionDto);
        SiteUser siteUser = userMapper.toEntity(userDto);

        question.getVoter().add(siteUser);

        this.questionRepository.save(question);
    }

}