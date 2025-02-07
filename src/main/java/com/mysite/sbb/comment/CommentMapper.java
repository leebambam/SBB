package com.mysite.sbb.comment;

import com.mysite.sbb.answer.AnswerMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toDto(Comment comment);
    Comment toEntity(CommentDto commentDto);
}
