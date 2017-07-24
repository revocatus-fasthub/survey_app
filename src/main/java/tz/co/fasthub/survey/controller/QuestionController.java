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
import java.util.List;

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

    public TalentValidator getTalentValidator() {
        return talentValidator;
    }

    public void setTalentValidator(TalentValidator talentValidator) {
        this.talentValidator = talentValidator;
    }

    @Autowired
    public QuestionController(QuestionService questionService, AnswerService answerService, TalentValidator talentValidator) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.talentValidator = talentValidator;
    }

    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public String list(Model model, Integer id) {
        model.addAttribute("questions", questionService.listAllQuestionsByAsc());
        //model.addAttribute("questions", questionService.listAllQuestionsByDesc());
        return "questions";
    }

    // View a specific question and answers by its id

    @RequestMapping("question/{qsnid}")
    public String showQuestion(@PathVariable Long qsnid, @Valid Answer answer, Model model, RedirectAttributes redirectAttributes) {
        Question question = questionService.getQsnById(qsnid);
        Long id = question.getId();
        if (answer.getAns() != null) {
            log.info(answer.toString());
            savedAnswer = answerService.saveByQnsId(answer, question);
        }
        model.addAttribute("answers", answerService.getAnswerByQsnId(question));
        model.addAttribute("question", questionService.getQsnById(qsnid));
        redirectAttributes.addFlashAttribute("flash.message.answer", "Answers Successfully Saved!");

        return "questionShow";
    }

    //Edit by its id

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
        talentValidator.validate(question, result);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("flash.message.question", "Error!");
            return "addQuestion";
        }
        savedQuestion = questionService.save(question);
        Long id = savedQuestion.getId();

        redirectAttributes.addFlashAttribute("flash.message.question", "Question " + id + " Successfully Saved!");
        return "redirect:question/" + id;

    }

    @RequestMapping(value = "questionSequence/{id}/{direction}", method = RequestMethod.GET)
    public String viewSequence(@PathVariable int id, @PathVariable String direction) {
        String up = "up", down = "down";
        Question selectedQuestion = questionService.getQnsBySequence1(id);
        Question questionBeforeSelectedQsn = null;
        Question questionAfterSelectedQsn = null;
    log.info("direction" + direction);
        log.info("sequence before = " + selectedQuestion.getSequence());
        if (selectedQuestion != null) {
            List<Question> questions = questionService.listAllQuestionsByAsc();
            for (int i = 0; i < questions.size(); i++) {
                if (questions.get(i).equals(selectedQuestion)) {

                    if (i==0){
                        questionBeforeSelectedQsn=questions.get(i);
                    }else {
                        questionBeforeSelectedQsn=questions.get(i-1);
                    }

                    if (questions.size()==(questions.indexOf(questions.get(i))+1)){
                        questionAfterSelectedQsn=selectedQuestion;
                    }else {
                        questionAfterSelectedQsn=questions.get(i+1);
                    }


                    if (direction.equals(up)) {
                        if (i > 0) {
                            selectedQuestion.setSequence(selectedQuestion.getSequence() - 1);
                            questionBeforeSelectedQsn.setSequence(questionBeforeSelectedQsn.getSequence() + 1);
                        }


                    }else if (direction.equals(down)){
                        if (questions.size()!=(questions.indexOf(questions.get(i))+1)){
                            selectedQuestion.setSequence(selectedQuestion.getSequence()+1);
                            questionAfterSelectedQsn.setSequence(questionAfterSelectedQsn.getSequence()-1);

                        }

                    }

                    break;

                }

            }

            if (selectedQuestion != null) {
                questionService.save(selectedQuestion);
            }
            if (questionBeforeSelectedQsn != null) {
                questionService.save(questionBeforeSelectedQsn);
            }
            if (questionAfterSelectedQsn != null) {
                questionService.save(questionAfterSelectedQsn);
            }
        }

        log.info("qsnArray: " + questionService.getQnsBySequence1(id));
        log.info("id: " + id);
        return "redirect:/questions";
    }


    @RequestMapping(value = "answerPosition/{id}/{direction}", method = RequestMethod.GET)
    public String viewPosition(@PathVariable Long id, @PathVariable String direction) {
        String up = "up", down = "down";

        Answer selectedAnswer = answerService.getAnswerById(id);
        Long qsnID = selectedAnswer.getQuestion().getId();
        Answer answerBeforeSelectedAns = null;
        Answer answerAfterSelectedAns = null;
        log.info("direction" + direction);
        log.info("position before = " + selectedAnswer.getPosition());
        if (selectedAnswer != null) {
            List<Answer> answers = answerService.listAllAnswersByDesc();
            for (int i = 0; i < answers.size(); i++) {
                if (answers.get(i).equals(selectedAnswer)) {

                    if (i==0){
                        answerBeforeSelectedAns=answers.get(i);
                    }else {
                        answerBeforeSelectedAns=answers.get(i-1);
                    }

                    if (answers.size()==(answers.indexOf(answers.get(i))+1)){
                        answerAfterSelectedAns=selectedAnswer;
                    }else {
                        answerAfterSelectedAns=answers.get(i+1);
                    }


                    if (direction.equals(up)) {
                        if (i > 0) {
                            selectedAnswer.setPosition(selectedAnswer.getPosition() + 1);
                            answerBeforeSelectedAns.setPosition(answerBeforeSelectedAns.getPosition() - 1);
                        }


                    }else if (direction.equals(down)){
                        if (answers.size()!=(answers.indexOf(answers.get(i)) - 1)){
                            selectedAnswer.setPosition(selectedAnswer.getPosition()+1);
                            answerAfterSelectedAns.setPosition(answerAfterSelectedAns.getPosition()-1);

                        }

                    }

                    break;

                }

            }

            if (selectedAnswer != null) {
                answerService.save(selectedAnswer);
            }
            if (answerBeforeSelectedAns != null) {
                answerService.save(answerBeforeSelectedAns);
            }
            if (answerAfterSelectedAns != null) {
                answerService.save(answerAfterSelectedAns);
            }
        }

        log.info("ansArray: " + answerService.getAnswerById(id));
        log.info("id: " + id);
        return "redirect:/question/"+qsnID;
    }


    @RequestMapping("question/delete/{id}")
    public String delete(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return "redirect:/questions";
    }

}
