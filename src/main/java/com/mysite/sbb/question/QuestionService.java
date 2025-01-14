package com.mysite.sbb.question;

import com.mysite.sbb.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

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
    */
    public List<QuestionDto> getList() {
        return this.questionRepository.findAll()
                .stream() // .stream()을 사용하여 List<Question>을 스트림으로 변환
                .map(QuestionDto::fromEntity) // Question 객체를 QuestionDto 객체로 변환
                .collect(Collectors.toList()); // map을 통해 변환된 스트림을 다시 List<QuestionDto>로 변환
    }

   /* public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }*/

    public QuestionDto getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return QuestionDto.fromEntity(question.get());
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
    }*/

    public void create(String subject, String content) {
        Question question = Question.builder()
                .subject(subject)
                .content(content)
                .createDate(LocalDateTime.now())
                .build();
        this.questionRepository.save(question);
    }
}