package com.mysite.sbb.comment;


import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.answer.AnswerDto;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.QuestionDto;
import com.mysite.sbb.question.QuestionRepository;
import com.mysite.sbb.user.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    // 댓글 조회
    public CommentDto getComment(Integer id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if(comment.isPresent()) {
            return commentMapper.toDto(comment.get());
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

    // 댓글 추가
    public CommentDto create(AnswerDto answerDto, String content, UserDto userDto) {

        CommentDto commentDto = CommentDto.builder()
                .content(content)
                .author(userDto)
                .build();

        // DTO를 엔티티로 변환
        Comment comment = commentMapper.toEntity(commentDto);

        Comment savedcomment = this.commentRepository.save(comment);

        return commentMapper.toDto(savedcomment);
    }


}
