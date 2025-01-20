package com.mysite.sbb.answer;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    //AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    @Mapping(source = "question", target = "question") // QuestionDto 매핑
    AnswerDto toDto(Answer answer);

    @Mapping(source = "question", target = "question") // Question 엔티티 매핑
    Answer toEntity(AnswerDto answerDto);
}
