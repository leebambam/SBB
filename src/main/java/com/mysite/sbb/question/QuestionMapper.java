package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerDto;
import com.mysite.sbb.answer.AnswerMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.List;

/*
    @Mapper : MapStruct에게 이 인터페이스를 매핑에 사용한다고 알려준다.
    (componentModel = "spring") : 스프링 프로젝트에서 자동으로 사용할 수 있도록 빈(bean)으로 등록 = @Autowired로 가져올 수 있게 만든다는 뜻
    target : 결과 객체의 필드 이름 (값을 넣을 곳).
    source : 원본 객체의 필드 이름 (값을 가져올 곳).


    @Mapping(target = "id", source = "id")
    @Mapping(target = "subject", source = "subject")
    @Mapping(target = "content", source = "content")
    -> 들어가 있지 않은 이유
    Question과 QuestionDto의 필드 이름이 완전히 동일하면 자동으로 매핑됩니다.
    즉, 이름이 같은 필드는 @Mapping 없이도 변환 가능합니다.

*/

@Mapper(componentModel = "spring", uses = {AnswerMapper.class})
public interface QuestionMapper {
    // QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    // 엔티티 → DTO 변환
     // Question의 answerList 필드를 QuestionDto의 answerList에 매핑.
    @Mapping(target = "answerPage", ignore = true)
    //@Mapping(target = "answerPage", source = "answerPage")
    QuestionDto toDto(Question question);

    // DTO → 엔티티 변환
    @Mapping(target = "answerList", source = "answerList")
    //@Mapping(target = "answerPage", source = "answerPage")
    Question toEntity(QuestionDto questionDto);

    // 엔티티 리스트 → DTO 리스트 변환
    List<QuestionDto> toDtoList(List<Question> questions);

/*    default Page<QuestionDto> toDtoList(Page<Question> questions) {
        return questions.map(this::toDto); // Page<Question>를 Page<QuestionDto>로 변환
    }*/
    /*
    questions : Page<Question> 객체로, 스프링 데이터 JPA에서 지원하는 페이징 클래스

    map : Page<T>의 map 메서드, 새로운 Page<R>를 반환

    this::toDto
        :  QuestionMapper 인터페이스의 toDto(Question question) 메서드를 참조
            (= 현재 QuestionMapper 객체에서 정의된 toDto 메서드를 사용하겠다는 뜻)

            this = 현재 QuestionMapper 객체
            toDto = 단일 Question 객체를 QuestionDto 객체로 변환
            페이지의 모든 Question 객체에 toDto를 적용하여 QuestionDto로 변환

    questions.map(this::toDto)가 호출되면
        map 메서드가 questions의 각 요소(Question)에 대해 toDto 메서드를 호출
        결과적으로, 모든 Question 객체가 QuestionDto 객체로 변환
    */

    // 엔티티 페이지 → DTO 페이지 변환
    default Page<QuestionDto> toDto(Page<Question> questions) {
        return questions.map(this::toDto); // map 메서드로 각 엔티티를 변환
    }


}
