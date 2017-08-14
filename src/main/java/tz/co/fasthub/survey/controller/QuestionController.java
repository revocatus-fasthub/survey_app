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
import tz.co.fasthub.survey.domain.CustomerTransaction;
import tz.co.fasthub.survey.domain.Question;
import tz.co.fasthub.survey.service.AnswerService;
import tz.co.fasthub.survey.service.CustomerTransactionService;
import tz.co.fasthub.survey.service.QuestionService;
import tz.co.fasthub.survey.validator.TalentValidator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static tz.co.fasthub.survey.constants.Constant.savedAnswer;
import static tz.co.fasthub.survey.constants.Constant.savedQuestion;

/**
 * Created by root on 7/17/17.
 */
@Controller
@RequestMapping("/survey/")
public class QuestionController {

    private final QuestionService questionService;
    private final AnswerService answerService;
    private final CustomerTransactionService customerTransactionService;

    private TalentValidator talentValidator;

    private static final Logger log = LoggerFactory.getLogger(QuestionController.class);

    public TalentValidator getTalentValidator() {
        return talentValidator;
    }

    public void setTalentValidator(TalentValidator talentValidator) {
        this.talentValidator = talentValidator;
    }

    @Autowired
    public QuestionController(QuestionService questionService, AnswerService answerService, CustomerTransactionService customerTransactionService, TalentValidator talentValidator) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.customerTransactionService = customerTransactionService;
        this.talentValidator = talentValidator;
    }

    @RequestMapping(value = "/questions", method = RequestMethod.GET)
    public String list(Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("questions", questionService.listAllQuestionsByAsc());
        redirectAttributes.addFlashAttribute("flash.message.question", "Success!");
        return "questions";
    }

    // View a specific question and answers by its id

    @RequestMapping("question/{qsnid}")
    public String showQuestion(@PathVariable Long qsnid, @Valid Answer answer, Model model, RedirectAttributes redirectAttributes) {
        Question question = questionService.getQsnById(qsnid);

            if (answer.getAns() != null) {
                savedAnswer = answerService.saveByQnsId(answer, question);
                redirectAttributes.addFlashAttribute("flash.message.answerSuccess", "Answer Successfully Saved!");
            }
            List<Answer> answers = answerService.getAnswerByQsnId(question);
            Map<String, Integer> barChartData = new HashMap<>();

        final List<Integer> listChoices = new ArrayList<Integer>();

        for (Answer answer1 : answers) {
            count(question, answer1);
            listChoices.add(answer1.getCount());
        }

        for (int i = 0; i < answers.size(); i++) {
            String key = answers.get(i).getAns();
            Integer counting = listChoices.get(i);
            barChartData.put(key,counting);
        }

//         barChartData.put(answers.get(0).getAns(), listChoices.get(0));//answers.indexOf(answer1)
//         barChartData.put(answers.get(1).getAns(), listChoices.get(1));//answer1.getCount()

        model.addAttribute("answers", answers);
        model.addAttribute("question", questionService.getQsnById(qsnid));
        model.addAttribute("barChartData", barChartData);

        return "questionShow";

    }

    @RequestMapping(value = "answer", method = RequestMethod.POST)
    public String saveAnswer(@Valid Answer answer, Model model, RedirectAttributes redirectAttributes){
        Question question = questionService.getQsnById(answer.getQuestion().getId());
        log.info("qsnid: "+question.getId());


        log.info(answer.toString());
            savedAnswer = answerService.saveByQnsId(answer, question);
        model.addAttribute("answers", answerService.getAnswerByQsnId(question));
        model.addAttribute("question", questionService.getQsnById(question.getId()));
        redirectAttributes.addFlashAttribute("flash.message.answerSuccess", "Answer Successfully Saved!");
        return "redirect:/survey/question/"+question.getId();
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

        model.addAttribute("questions", questionService.listAllQuestionsByAsc());
    //'    redirectAttributes.addFlashAttribute("flash.message.question", "Success!");

        return "addQuestion";
    }

    //save edited question

    @RequestMapping(value = "editedQuestion", method = RequestMethod.POST)
    public String saveEditedQuestion(@Valid Question question, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        model.addAttribute("Question", question);
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("flash.message.question", "Error!");
            return "addQuestion";
        }
        savedQuestion = questionService.update(question);
        Long id = savedQuestion.getId();

        redirectAttributes.addFlashAttribute("flash.message.question", "Question " + id + " Successfully Updated!");
        return "redirect:/survey/question/" + id;

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
        return "redirect:/survey/question/" + id;

    }

    @RequestMapping(value = "questionSequence/{id}/{direction}", method = RequestMethod.GET)
    public String viewSequence(@PathVariable int id, @PathVariable String direction) {
        Question selectedQuestion = questionService.getQsnById(Long.valueOf(id));
        Question questionBeforeSelectedQsn = null, questionAfterSelectedQsn = null;
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
                        questionAfterSelectedQsn=questions.get(i);
                    }else {
                        questionAfterSelectedQsn=questions.get(i+1);
                    }


                    if (direction.equals("up")) {
                        if (i > 0) {
                            selectedQuestion.setSequence(selectedQuestion.getSequence() - 1);
                            questionBeforeSelectedQsn.setSequence(questionBeforeSelectedQsn.getSequence() + 1);
                        }


                    }else if (direction.equals("down")){
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

        return "redirect:/survey/addQuestion";
    }


    @RequestMapping(value = "answerPosition/{question_id}/{answer_id}/{direction}", method = RequestMethod.GET)
    public String answerSequence(@PathVariable Long question_id,@PathVariable Long answer_id, @PathVariable String direction, Question question) {
        String up = "up", down = "down";

        Answer selectedAnswer = answerService.getAnswerById(answer_id);
        Question fetchedQuestion = selectedAnswer.getQuestion();

        Answer answerBeforeSelectedAns = null, answerAfterSelectedAns = null;
        if (selectedAnswer != null) {
            List<Answer> answers = answerService.getAnswerByQsnId(fetchedQuestion);
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
                            selectedAnswer.setPosition(selectedAnswer.getPosition() - 1);
                            answerBeforeSelectedAns.setPosition(answerBeforeSelectedAns.getPosition() + 1);
                        }


                    }else if (direction.equals(down)){
                        if (answers.size()!=(answers.indexOf(answers.get(i))+1)){
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

        return "redirect:/survey/question/"+fetchedQuestion.getId();
    }


    @RequestMapping("question/delete/{id}")
    public String deleteQuestion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if(id!=null){
                questionService.deleteQuestion(id);
                redirectAttributes.addFlashAttribute("flash.message.questionSuccess", "Question with id "+id+" has been successfully deleted");
                return "redirect:/survey/addQuestion";
            }
            else {
                redirectAttributes.addFlashAttribute("flash.message.questionError", "Error! \nCannot delete question with id "+id+". This question has other details in the Customer Details list");
                return "redirect:/survey/addQuestion";
            }

        }catch (Exception e){
            redirectAttributes.addFlashAttribute("flash.message.questionError", "Failed" +
                    "! \nCannot delete question with id "+id+". \n\nThis question has other details in the Customer Details list" );
            return "redirect:/survey/addQuestion";
        }

    }

    @RequestMapping("answer/delete/{qsdId}/{id}")
    public String deleteAnswer(@PathVariable Long qsdId, @PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Answer answer = answerService.getAnswerById(id);

        try {
            if (answer!=null) {
                answerService.deleteAnswer(id);
                model.addAttribute("question", questionService.getQsnById(qsdId));
                redirectAttributes.addFlashAttribute("flash.message.answerSuccess", "Answer with id "+id+" has been successfully deleted");
                return "redirect:/survey/question/"+qsdId;
            }else {
                redirectAttributes.addFlashAttribute("flash.message.answerError", "Answer not found with id :"+id);
                return "redirect:/survey/question/"+qsdId;

            }
        }catch (Exception e){
                        redirectAttributes.addFlashAttribute("flash.message.answerError", "Error! \nCannot delete answer with id "+id+"." );
                        return "redirect:/survey/question/"+qsdId;
                }

    }

    public void count(Question question, Answer answer ) {


        if(question != null && answer != null){

            List<CustomerTransaction> customerTransactions = customerTransactionService.getAllQuestionAndAnswer(question, answer);

            if(customerTransactions!=null && !customerTransactions.isEmpty()){

                answer.setCount(customerTransactions.size());
            }
        }

    }

}
