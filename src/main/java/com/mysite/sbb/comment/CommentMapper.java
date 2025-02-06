package com.mysite.sbb.comment;

import com.mysite.sbb.user.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    CommentDto toDto(Comment comment);

    Comment toEntity(CommentDto commentDto);
}
