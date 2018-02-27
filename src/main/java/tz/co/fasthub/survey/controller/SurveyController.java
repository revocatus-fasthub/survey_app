package tz.co.fasthub.survey.controller;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import tz.co.fasthub.survey.constants.Constant;
import tz.co.fasthub.survey.domain.*;
import tz.co.fasthub.survey.service.*;

import java.util.ArrayList;
import java.util.List;

import static tz.co.fasthub.survey.constants.Constant.restTemplate;

/**
 * Created by naaminicharles on 6/20/17.
 */
@RestController
@RequestMapping(value = "/sms/utc")
public class SurveyController {
//http://survey.fasthub.co.tz:8081/sms/utc?id=74&serviceNumber=665656&text=Test&msisdn=254791199624&date=2016-05-18&operator=safaricom-soap
    //http://127.0.0.1:8081/sms/utc?id=74&serviceNumber=0785723360&text=FastHub&msisdn=255754088816&date=2016-05-18&operator=safaricom-soap

    private static final Logger log = LoggerFactory.getLogger(SurveyController.class);
    @Autowired
    private SurveyMonkeyController surveyMonkeyController;
    private QuestionService questionService;
    private AnswerService answerService;
    private SurveyService surveyService;
    private CustomerService customerService;
    private CustomerTransactionService customerTransactionService;

    @Autowired
    public SurveyController(SurveyService surveyService, QuestionService questionService, AnswerService answerService, CustomerService customerService, CustomerTransactionService customerTransactionService) {
        this.surveyService = surveyService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.customerService = customerService;
        this.customerTransactionService = customerTransactionService;
    }

    public static int parseIntInput(String intInput) {

        int returnResult = 0;
        try {
            returnResult = Integer.parseInt(intInput);
        } catch (NumberFormatException e) {
            e.getMessage();
        } catch (Exception e) {
            e.getMessage();
        }

        return returnResult;

    }

    private static void sendAMessageToGravity(String id, String msisdn, String response, String serviceNumber) {
        try {
            List<MessageHandler> messages = new ArrayList<>();
            MessageHandler messageHandler = new MessageHandler(id, response, msisdn, serviceNumber);
            messages.add(messageHandler);

            Content content = new Content(Constant.channelLink, messages);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            log.info(String.valueOf(content));
            HttpEntity<Content> entity = new HttpEntity<>(content, headers);
            log.info(String.valueOf(entity));
            Object responseMEssage = restTemplate.postForObject(Constant.GW_URL, entity, Object.class);
            log.info("response from gravity: " + responseMEssage);
        } catch (RestClientException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(params = {"id", "serviceNumber", "text", "msisdn", "date", "operator"}, method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity index(@RequestParam("id") String id,
                                @RequestParam("serviceNumber") String serviceNumber,
                                @RequestParam("text") String text,
                                @RequestParam("msisdn") String msisdn,
                                @RequestParam("date") String date,
                                @RequestParam("operator") String operator) throws JSONException {

        log.info("received message from gravity: " + text + " from " + msisdn);
        String response = null;


        Customer customer = customerService.getCustomerByMsisdn(msisdn);
        CustomerTransaction customerTransaction = null;
        Answer preselectedAnswer = null;
        Answer lastSentAnswer = null;


        if (customer != null) {
            customerTransaction = customerTransactionService.getOneTransactionByCustomerDesc(customer, false);

            if (customerTransaction != null) {
                log.info("Found Existing Transaction: " + customerTransaction);
                Answer answer = answerService.getAllByQuestionAndPosition(customerTransaction.getQuestion(), parseIntInput(text));
                if (answer != null || customerTransaction.getQuestion().getType().equals("Open Ended")) {

                    if (customerTransaction.getAnswer() != null) {
                        response = fetchNextQuestion(customerTransaction);
                    } else {
                        customerTransaction.setAnswer(answer);
                        customerTransaction.setAnswerDetails(text);
                        customerTransaction.setAttended(true);
                        response = fetchNextQuestion(customerTransaction);
                    }
                } else {
                    response = "Uchaguzi si sahihi, jaribu tena" + "\n\n" + fetchPreviousQuestion(customerTransaction);
                }
            } else {
                Question questionOne = questionService.getQnOneBySequence();
                if (questionOne != null) {
                    customerTransaction = new CustomerTransaction(customer, questionOne);


                    for (Answer answer : questionOne.getAnswer()) {
                        if (text != null && parseIntInput(text) != 0 && parseIntInput(text) == answer.getPosition()) {
                            preselectedAnswer = answer;
                            break;
                        }
                    }
                    if (preselectedAnswer == null) {
                        response = this.getQuestionOne(questionOne);
                    } else if (preselectedAnswer != null) {
                        customerTransaction.setAnswerDetails(text);
                        response = fetchNextQuestion(customerTransaction);
                    }
                } else {
                    response = "Samahani, hakuna swali la ziada";
                }
            }
        } else {

            customer = new Customer(msisdn);
            Customer createdCustomer = customerService.saveCustomer(customer);
            Question questionOne = questionService.getQnOneBySequence();


            if (questionOne != null) {


                for (Answer answer : questionOne.getAnswer()) {
                    if (text != null && parseIntInput(text) != 0 && parseIntInput(text) == answer.getPosition()) {
                        preselectedAnswer = answer;
                        break;
                    }
                }
                customerTransaction = new CustomerTransaction(createdCustomer, questionOne);
                if (preselectedAnswer == null) {
                    response = this.getQuestionOne(questionOne);
                } else if (preselectedAnswer != null) {
                    customerTransaction.setAnswerDetails(text);
                    response = fetchNextQuestion(customerTransaction);
                }


            } else {
                response = "Samahani, hakuna swali la ziada";
            }

        }

        if (customerTransaction != null) {
            CustomerTransaction customerTransaction1 = customerTransactionService.saveCustomerTransaction(customerTransaction);

            if (customerTransaction1 != null && preselectedAnswer != null) {
                customerTransaction1.setAnswer(preselectedAnswer);
                customerTransaction1.setAnswerDetails(String.valueOf(preselectedAnswer));//************
                customerTransaction1.setAttended(true);
                customerTransactionService.saveCustomerTransaction(customerTransaction1);
            }


        }


        if (response != null) {
            sendAMessageToGravity(id, msisdn, response, serviceNumber);
        }

        return new ResponseEntity(HttpStatus.OK);
    }

    private String fetchNextQuestion(CustomerTransaction customerTransaction) {
        String response = null;
        try {
            Question nextQuestionquestion = questionService.getNextQuestion(customerTransaction.getQuestion());
            if (nextQuestionquestion != null) {
                response = answerService.getAnswerByQuestion(nextQuestionquestion);
                customerTransactionService.saveCustomerTransaction(new CustomerTransaction(customerTransaction.getCustomer(), nextQuestionquestion));

            } else {
                response = "Asante.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private String fetchPreviousQuestion(CustomerTransaction customerTransaction) {
        String response = null;
        try {

            if (customerTransaction.getQuestion() != null) {
                response = answerService.getAnswerByQuestion(customerTransaction.getQuestion());
            } else {
                response = "Asante.";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private String getQuestionOne(Question question) {
        String response = null;
        if (question != null) {
            response = answerService.getAnswerByQuestion(question); //before it had this question.getQsn() +"\n"+ which resulted into sending qsn text twice
        }
        return response;
    }


}