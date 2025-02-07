package com.mysite.sbb.answer;

import com.mysite.sbb.comment.CommentMapper;
import com.mysite.sbb.question.QuestionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring", uses = {QuestionMapper.class, CommentMapper.class})
public interface AnswerMapper {
    //AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);

    // Answer -> AnswerDto 변환
    @Mapping(source = "question", target = "question") // QuestionDto 매핑
    @Mapping(source = "commentList", target = "commentList") // CommentDto 매핑
    //@Mapping(target = "answerPage", ignore = true)
    AnswerDto toDto(Answer answer);

    // AnswerDto -> Answer 변환
    @Mapping(source = "question", target = "question") // Question 엔티티 매핑
    @Mapping(source = "commentList", target = "commentList") // Comment 엔티티 매핑
    Answer toEntity(AnswerDto answerDto);

    // Page<Answer> -> Page<AnswerDto> 변환
    default Page<AnswerDto> toDtoPage(Page<Answer> answersPage) {
        return answersPage.map(this::toDto);
    }

    default Page<Answer> toEntity(Page<AnswerDto> answerDtos) {
        return answerDtos.map(this::toEntity);
    }
}
