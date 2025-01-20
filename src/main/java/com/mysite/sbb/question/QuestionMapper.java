package com.mysite.sbb.question;

import com.mysite.sbb.answer.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

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

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    // QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    // 엔티티 → DTO 변환
    @Mapping(target = "answerList", source = "answerList") // Question의 answerList 필드를 QuestionDto의 answerList에 매핑.
    QuestionDto toDto(Question question);

    // DTO → 엔티티 변환
    @Mapping(target = "answerList", source = "answerList")
    Question toEntity(QuestionDto questionDto);

    // 엔티티 리스트 → DTO 리스트 변환
    List<QuestionDto> toDtoList(List<Question> questions);
}
