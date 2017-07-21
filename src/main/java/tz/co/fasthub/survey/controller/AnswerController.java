package tz.co.fasthub.survey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.service.AnswerService;
import tz.co.fasthub.survey.service.QuestionService;

/**
 * Created by root on 7/17/17.
 */
@Controller
@RequestMapping("/answerpage")
public class AnswerController {

    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);


    private SurveyMonkeyController surveyMonkeyController;

    private final AnswerService answerService;

    private final QuestionService questionService;

    @Autowired
    public AnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @RequestMapping(value = "/answers", method = RequestMethod.GET)
    public String listAnswers(Model model) {

        model.addAttribute("answers", answerService.listAllAnswers());
        return "answers";
    }

    // View a specific talent by its id

    @RequestMapping("answer/{id}")
    public String showAnswers(@PathVariable Long id, Model model) {
        model.addAttribute("answer", answerService.getAnswerById(id));
        return "answerShow";
    }

    //Edit/Update by its id

    @RequestMapping("answer/edit/{id}")
    public String editAnswer(@PathVariable Long id, Model model) {
        model.addAttribute("answer", answerService.getAnswerById(id));
        return "answerEditForm";
    }

    // New ANswer

    @RequestMapping("/addanswer/{id}")
    public String newAnswer(@PathVariable Long id, Model model) {
       // id = savedQuestion.getId();

      //  model.addAttribute("question", questionService.getQsnById(id) );
        model.addAttribute("answer", new Answer());
        return "addAnswer";
    }

    @RequestMapping("answer/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        answerService.deleteAnswer(answerService.getAnswerById(id));
        if(id!=null){
            redirectAttributes.addFlashAttribute("flash.message.answer", "Answer Successfully Deleted!");
            return "questionShow";
        }
        redirectAttributes.addFlashAttribute("flash.message", "This Answer with id "+answerService.getAnswerById(id)+" doesn't exist");

        return "questionShow";
    }
}
