package com.mysite.sbb.question;

import com.mysite.sbb.answer.AnswerForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/question")
@RequiredArgsConstructor
@Controller
public class QuestionController {

    private final QuestionService questionService;


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
    */
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }

        // QuestionForm을 QuestionDto로 변환
        QuestionDto questionDto = QuestionDto.builder()
                .subject(questionForm.getSubject())
                .content(questionForm.getContent())
                .build();

        //this.questionService.create(questionForm.getSubject(), questionForm.getContent());
        this.questionService.create(questionDto);
        return "redirect:/question/list";
    }


}