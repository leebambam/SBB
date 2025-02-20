package com.mysite.sbb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import com.mysite.sbb.answer.AnswerService;
import com.mysite.sbb.question.QuestionDto;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.UserDto;
import com.mysite.sbb.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.mysite.sbb.answer.Answer;
import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionRepository;

@SpringBootTest
class SbbApplicationTests {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    //@Transactional
    @Test
    void testJpa() {
        /*Optional<Question> oq = this.questionRepository.findById(2);
        assertTrue(oq.isPresent());
        Question q = oq.get();

        List<Answer> answerList = q.getAnswerList();

        assertEquals(1, answerList.size());
        assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());*/

        QuestionDto question = this.questionService.getQuestion(323);
        UserDto userDto = this.userService.getUser("test01");

        for (int i = 1; i <= 300; i++) {
            String content = String.format("테스트 답변 데이터입니다:[%03d]", i);
            this.answerService.create(question, content,userDto);
        }
    }
}