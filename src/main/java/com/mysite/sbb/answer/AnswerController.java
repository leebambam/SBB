package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.question.QuestionDto;
import com.mysite.sbb.question.QuestionService;
import com.mysite.sbb.user.UserDto;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;


    /*

        로그인한 사용자의 정보를 알려면 스프링 시큐리티가 제공하는 Principal 객체를 사용해야 한다.

        principal.getName()을 호출하면 현재 로그인한 사용자의 사용자명(사용자ID)을 알 수 있다.

    */

    // 답변 등록
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String createAnswer(Model model, @PathVariable("id") Integer id,
                                @Valid AnswerForm answerForm, BindingResult bindingResult, Principal principal) {
        // Question question = this.questionService.getQuestion(id);
        QuestionDto question = this.questionService.getQuestion(id);
        UserDto userDto = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }

        //this.answerService.create(question, answerForm.getContent(), userDto);
        //return String.format("redirect:/question/detail/%s", id);
        AnswerDto answerDto = this.answerService.create(question, answerForm.getContent(), userDto);
        return String.format("redirect:/question/detail/%s#answer_%s", answerDto.getQuestion().getId(), answerDto.getId());
    }

    // 답변 조회하기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id, Principal principal) {

        AnswerDto answerDto = this.answerService.getAnswer(id);

        if (!answerDto.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        // 답변 수정 시 기존의 답변 내용이 필요하므로 AnswerForm 객체에 조회한 값을 저장하여 리턴
        answerForm.setContent(answerDto.getContent());
        return "answer_form";
    }

    // 답변 수정하기
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String answerModify(@Valid AnswerForm answerForm, BindingResult bindingResult, @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        AnswerDto answerDto = this.answerService.getAnswer(id);

        if (!answerDto.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answerDto, answerForm.getContent());
        //return String.format("redirect:/question/detail/%s", answerDto.getQuestion().getId());
        return String.format("redirect:/question/detail/%s#answer_%s", answerDto.getQuestion().getId(), answerDto.getId());
    }

    // 답변 삭제하기
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") Integer id) {
        AnswerDto answerDto = this.answerService.getAnswer(id);

        if (!answerDto.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }

        this.answerService.delete(answerDto);

        return String.format("redirect:/question/detail/%s", answerDto.getQuestion().getId());
    }

    // 사용자(siteUser)를 추천인(voter)으로 저장
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String answerVote(Principal principal, @PathVariable("id") Integer id) {
        AnswerDto answerDto = this.answerService.getAnswer(id);
        UserDto userDto = this.userService.getUser(principal.getName());

        this.answerService.vote(answerDto, userDto);
        //return String.format("redirect:/question/detail/%s", answerDto.getQuestion().getId());
        return String.format("redirect:/question/detail/%s#answer_%s", answerDto.getQuestion().getId(), answerDto.getId());
    }
}