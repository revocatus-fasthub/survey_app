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
import tz.co.fasthub.survey.domain.Question;
import tz.co.fasthub.survey.service.AnswerService;
import tz.co.fasthub.survey.service.QuestionService;
import tz.co.fasthub.survey.validator.TalentValidator;

import javax.validation.Valid;

import static tz.co.fasthub.survey.constants.Constant.savedAnswer;
import static tz.co.fasthub.survey.constants.Constant.savedQuestion;

/**
 * Created by root on 7/17/17.
 */
@Controller
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;

    private TalentValidator talentValidator;

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

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
        model.addAttribute("questions", questionService.listAllQuestionsByDesc());
        //model.addAttribute("questions", questionService.listAllQuestionsByDesc());
        return "questions";
    }

    // View a specific question and answers by its id

    @RequestMapping("question/{qsnid}")
    public String showQuestion(@PathVariable Long qsnid, @Valid Answer answer, Model model, RedirectAttributes redirectAttributes) {
        Question question = questionService.getQsnById(qsnid);
        Long id = question.getId();
        if(answer.getAns()!=null) {
            log.info(answer.toString());
            savedAnswer = answerService.saveByQnsId(answer, question);
        }
        model.addAttribute("answers", answerService.getAnswerByQsnId(question));
        model.addAttribute("question", questionService.getQsnById(qsnid));
        redirectAttributes.addFlashAttribute("flash.message.answer", "Answers Successfully Saved!");

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

    }

    @RequestMapping(value = "questionSequence1/{id}", method = RequestMethod.GET)
    public String viewSequence1 (@PathVariable int id){
        log.info("qsnArray1: "+questionService.getQnsBySequence1(id) );
        log.info("id1: "+id);
        return "redirect:/questions";
    }


    @RequestMapping(value = "questionSequence2/{id}", method = RequestMethod.GET)
    public String viewSequence2 (@PathVariable int id){
        questionService.getQnsBySequence2(id);
        log.info("qsnArray2: "+questionService.getQnsBySequence2(id) );
        log.info("id2: "+id);
        return "redirect:/questions";
    }

  /*  @RequestMapping("product/delete/{id}")
    public String delete(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return "redirect:/questions";
    }
*/

}
