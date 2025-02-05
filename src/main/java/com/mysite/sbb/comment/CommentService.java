package com.mysite.sbb.comment;


import com.mysite.sbb.DataNotFoundException;
import com.mysite.sbb.answer.AnswerRepository;
import com.mysite.sbb.question.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
