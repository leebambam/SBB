package com.mysite.sbb.comment;

import com.mysite.sbb.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CommentMapper {

    //Comment 엔티티를 CommentDto로 변환
    @Mapping(source = "author", target = "author")
    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "answer.id", target = "answerId")
    CommentDto toDto(Comment comment);

    @Mapping(source = "questionId", target = "question.id")
    @Mapping(source = "answerId", target = "answer.id")
    Comment toEntity(CommentDto commentDto);
}
