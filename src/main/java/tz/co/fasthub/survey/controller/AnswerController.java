package tz.co.fasthub.survey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.service.AnswerService;
import tz.co.fasthub.survey.service.QuestionService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static tz.co.fasthub.survey.constants.Constant.savedQuestion;

/**
 * Created by root on 7/17/17.
 */
@Controller
@RequestMapping("/answerpage")
public class AnswerController {

    private static final Logger log = LoggerFactory.getLogger(AnswerController.class);

    @Autowired
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

    // New talent

    @RequestMapping("/addanswer/{id}")
    public String newAnswer(@PathVariable Long id, Model model) {
       // id = savedQuestion.getId();

      //  model.addAttribute("question", questionService.getQsnById(id) );
        model.addAttribute("answer", new Answer());
        return "addAnswer";
    }

    // Save talent to database

    @RequestMapping(value = "/answer", method = RequestMethod.POST)
    public String saveAnswer(Long id, @Valid Answer answer, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if(result.hasErrors()){
            return "addAnswer";
        }
        List<Answer> answerArrayList = new ArrayList<Answer>();
     /*   for(int i =0; i<answerArrayList.size(); i++){
            id = savedQuestion.getId();
            Answer savedAnser = answerService.save(answer,id);
        }*/

       /* String answer1 = answer.getAns();
        String position1 = answer.getPosition();
        String savedAns= position1+answer1;
        log.info(savedAns);
     */   //List<Answer> answerArrayList = new ArrayList<Answer>();
       // for(Answer ans : answerArrayList){
            //id = savedQuestion.getId();
          //  Answer savedAnswers = answerService.save(answer,id);
       // }
      //  answer.setAns(answerArrayList);
        /*for(int i=0; i<5;i++){
            answerArrayList.add(new Answer());


        }*/
        //answer.setAns(answerArrayList);
        answerArrayList.add(answer);
        id = savedQuestion.getId();
        Answer savedAnswers = answerService.save(answer,id);
        model.addAttribute("Answer", answer);


        redirectAttributes.addFlashAttribute("flash.message", "Answers Successfully Saved!");
        return "redirect:/answerpage/answers";

    }

    // Delete talent by its id
/*
    @RequestMapping("talent/delete/{id}")//, @PathVariable Integer id
    public String deleteTalent(@PathVariable Integer id, RedirectAttributes redirectAttributes) throws NotFoundException {
        if(id!=null){
            AnswerService.deleteTalent(id);
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
