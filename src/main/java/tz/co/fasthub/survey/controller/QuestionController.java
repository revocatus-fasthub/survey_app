package tz.co.fasthub.survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.survey.domain.Question;
import tz.co.fasthub.survey.service.QuestionService;
import tz.co.fasthub.survey.validator.TalentValidator;

import javax.validation.Valid;

/**
 * Created by root on 7/17/17.
 */
@Controller
public class QuestionController {

    private final QuestionService questionService;

    private TalentValidator talentValidator;

    public TalentValidator getTalentValidator(){
        return talentValidator;
    }

    public void setTalentValidator(TalentValidator talentValidator){
        this.talentValidator=talentValidator;
    }

    @Autowired
    public QuestionController(QuestionService questionService, TalentValidator talentValidator) {
        this.questionService = questionService;
        this.talentValidator = talentValidator;
    }

    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public String list(Model model, Integer id) {
      //  questionService.getQnsBySequence(id);
        model.addAttribute("questions", questionService.listAllTalent());
        //model.addAttribute("questions", questionService.getQsnByDescendingId(id));
        return "questions";
    }

    // View a specific talent by its id

    @RequestMapping("question/{id}")
    public String showQuestion(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.getQsnById(id));
        return "questionShow";
    }

    //Edit/Update by its id

    @RequestMapping("question/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("question", questionService.getQsnById(id));
        return "questionEditForm";
    }

    // New talent

    @RequestMapping("/addQuestion")
    public String newQuestion(Model model) {
        model.addAttribute("question", new Question());
        return "addQuestion";
    }

    // Save talent to database

    @RequestMapping(value = "question", method = RequestMethod.POST)
    public String saveQuestion(@Valid Question question, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("Question", question);
        talentValidator.validate(question,result);
        if(result.hasErrors()){
            redirectAttributes.addFlashAttribute("flash.message", "Error!");
            return "addQuestion";
        }

        Question savedQuestion = questionService.save(question);

        redirectAttributes.addFlashAttribute("flash.message", "Question Successfully Saved!");
        return "redirect:answerpage/addanswer";

    }

    // Delete talent by its id
/*
    @RequestMapping("talent/delete/{id}")//, @PathVariable Integer id
    public String deleteTalent(@PathVariable Integer id, RedirectAttributes redirectAttributes) throws NotFoundException {
        if(id!=null){
            questionService.deleteTalent(id);
            deleteTwitterTalentAccount(Long.valueOf(id),redirectAttributes);
        }
        //twitterTalentAccountService.deleteTalentById(id);
        redirectAttributes.addFlashAttribute("flash.message", "Talent Successfully Deleted!");
        return "redirect:/talents";
    }*/
/*
    @RequestMapping("talent/deleteTwitterTalent/{id}")//
    public String deleteTwitterTalentAccount(@PathVariable Long id, RedirectAttributes redirectAttributes) throws NotFoundException {
        twitterTalentAccountService.deleteTalentById(id);
        if(id!=null){
            redirectAttributes.addFlashAttribute("flash.message", "Talent Successfully Deleted!");
            return "redirect:/talents";
        }
        redirectAttributes.addFlashAttribute("flash.message", "This Twitter Talent Account with id "+twitterTalentAccountService.getTalentById(id)+" doesn't exist");
        return "redirect:/talents";
    }*/
}
