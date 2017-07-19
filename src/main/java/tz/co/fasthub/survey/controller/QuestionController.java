package tz.co.fasthub.survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.domain.Question;
import tz.co.fasthub.survey.service.AnswerService;
import tz.co.fasthub.survey.service.QuestionService;
import tz.co.fasthub.survey.validator.TalentValidator;

import javax.validation.Valid;

import static tz.co.fasthub.survey.constants.Constant.savedQuestion;

/**
 * Created by root on 7/17/17.
 */
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    private TalentValidator talentValidator;

    public TalentValidator getTalentValidator(){
        return talentValidator;
    }

    public void setTalentValidator(TalentValidator talentValidator){
        this.talentValidator=talentValidator;
    }

    @Autowired
    public QuestionController(QuestionService questionService, AnswerService answerService, TalentValidator talentValidator) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.talentValidator = talentValidator;
    }

    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public String list(Model model, Integer id) {
      //  questionService.getQnsBySequence(id);
        model.addAttribute("questions", questionService.listAllTalent());
        //model.addAttribute("questions", questionService.getQsnByDescendingId(id));
        return "questions";
    }

    // View a specific question by its id

    @RequestMapping("question/{id}")
    public String showQuestion(@PathVariable Long id, Long ansId, @Valid Answer answer, Model model) {
        //ansId = answerService.getAnswerById()
       //model.addAttribute("answer",answerService.getAnswerByQsnId(id));
        //  model.addAttribute("answer", answerService.getAnswerById(ansId));
        model.addAttribute("question", questionService.getQsnById(id));
        model.addAttribute("answer", answer);
        return "questionShow";
    }

    //Edit/Update by its id

    @RequestMapping("question/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.getQsnById(id));
        return "questionEditForm";
    }

    // New question

    @RequestMapping("/addQuestion")
    public String newQuestion(Model model) {
        model.addAttribute("question", new Question());
        return "addQuestion";
    }

    // Save question to database

    @RequestMapping(value = "question", method = RequestMethod.POST)
    public String saveQuestion(@Valid Question question, @Valid Answer answer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("Question", question);
        talentValidator.validate(question,result);
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("flash.message.question", "Error!");
            return "addQuestion";
        }

        savedQuestion = questionService.save(question);
        Long id = savedQuestion.getId();

        redirectAttributes.addFlashAttribute("flash.message.question", "Question "+ id +" Successfully Saved!");
       return "redirect:question/"+id;
        // return "redirect:answerpage/addanswer/"+id;

    }


}
