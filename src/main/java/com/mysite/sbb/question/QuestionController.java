package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import com.mysite.sbb.user.UserDto;
import com.mysite.sbb.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final UserService userService;


    /*
    @RequestMapping("/list")
    public String list(Model model) {
        // List<Question> questionList = this.questionService.getList();
        List<QuestionDto> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }

    http://localhost:8080/question/list?page=0와 같이
    GET 방식으로 요청된 URL에서 page값을 가져오기 위해

    list 메서드의 매개변수로 @RequestParam(value="page", defaultValue="0") int page가 추가되었다.

    GET 방식에서는 값을 전달하기 위해서 ?와 & 기호를 사용한다.
    첫 번째 파라미터는 ? 기호를 사용하고 그 이후 추가되는 값은 & 기호를 사용한다.

    */
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page) {
        Page<QuestionDto> paging = this.questionService.getList(page);
        model.addAttribute("paging", paging);
        return "question_list";
    }

    @RequestMapping(value = "/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        //Question question = this.questionService.getQuestion(id);
        QuestionDto question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm) {
        return "question_form";
    }

    /*
        QuestionForm을 컨트롤러에서 사용할 수 있도록
        subject, content 대신 매개변수로 추가 됨.

        subject, content 항목을 지닌 폼이 전송되면
        QuestionForm의 subject, content 속성이 자동으로 바인딩된다.

        @Valid 애너테이션을 적용하면 QuestionForm의 @NotEmpty, @Size 등으로 설정한 검증 기능이 동작한다.
        BindingResult 매개변수는 @Valid 애너테이션으로 검증이 수행된 결과를 의미하는 객체

        @PreAuthorize("isAuthenticated()")
        : 애너테이션이 붙은 메서드는 로그인한 경우에만 실행된다.
        로그아웃 상태에서 호출되면 로그인 페이지로 강제 이동된다.
    */

    // 질문 등록
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        // QuestionForm을 QuestionDto로 변환
        QuestionDto questionDto = QuestionDto.builder()
                .subject(questionForm.getSubject())
                .content(questionForm.getContent())
                .build();

        UserDto userDto = this.userService.getUser(principal.getName());

        //this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        this.questionService.create(questionDto, userDto);
        return "redirect:/question/list";
    }


    // 질문 수정 화면
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id, Principal principal) {
        QuestionDto questionDto = this.questionService.getQuestion(id);

        // 현재 로그인한 사용자와 질문의 작성자가 동일하지 않을 경우
        if(!questionDto.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        //  수정할 질문의 제목과 내용을 화면에 보여 주기 위해 questionForm 객체에 id값으로 조회한
        //  질문의 제목(subject)과 내용(object)의 값을 담아서 템플릿으로 전달했다.
        questionForm.setSubject(questionDto.getSubject());
        questionForm.setContent(questionDto.getContent());

        return "question_form";
    }

    // 질문 수정하기
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult, Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        QuestionDto questionDto = this.questionService.getQuestion(id);

        // 로그인한 사용자와 수정하려는 질문의 작성자가 동일한지도 검증
        if (!questionDto.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.modify(questionDto, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }
}