package tz.co.fasthub.survey.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tz.co.fasthub.survey.constants.Constant;
import tz.co.fasthub.survey.domain.*;
import tz.co.fasthub.survey.service.AnswerService;
import tz.co.fasthub.survey.service.QuestionService;
import tz.co.fasthub.survey.service.SurveyService;

import java.util.ArrayList;
import java.util.List;

import static tz.co.fasthub.survey.constants.Constant.restTemplate;

/**
 * Created by Naamini on 6/20/17.
 */
@RestController
@RequestMapping(value = "/sms/utc")
public class SurveyController {
//http://survey.fasthub.co.tz:8081/sms/utc?id=74&serviceNumber=665656&text=Test&msisdn=254791199624&date=2016-05-18&operator=safaricom-soap
    //http://127.0.0.1:8081/sms/utc?id=74&serviceNumber=0785723360&text=FastHub&msisdn=255754088816&date=2016-05-18&operator=safaricom-soap

    @Autowired
    private SurveyMonkeyController surveyMonkeyController;

    private final QuestionService questionService;

    private final AnswerService answerService;

    private final SurveyService surveyService;

    private static final Logger log = LoggerFactory.getLogger(SurveyController.class);

    @Autowired
    public SurveyController(SurveyService surveyService, QuestionService questionService, AnswerService answerService) {
        this.surveyService = surveyService;
        this.questionService = questionService;
        this.answerService = answerService;
    }

    @RequestMapping(params = {"id", "serviceNumber", "text", "msisdn", "date", "operator"}, method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity index (@RequestParam("id") String id,
                                 @RequestParam("serviceNumber") String serviceNumber,
                                 @RequestParam("text") String text,
                                 @RequestParam("msisdn") String msisdn,
                                 @RequestParam("date") String date,
                                 @RequestParam("operator") String operator) throws JSONException {

        log.info("received message from gravity: "+text+ " from "+msisdn);
        String response = null;
        String replyTo="\nSend your response to : 0785723360";

        switch (text) {
            case "FastHub":
              //  response = surveyMonkeyController.loopQsns() +replyTo;//surveyMonkeyController.getQsnOne()+"\n"+replyTo;
              Question qsn = questionService.getQsnById(14L);
          //      List<Answer> answer = answerService.getAnswerByQsnId(14L);
             // log.info(String.valueOf(answer));
              JSONObject array = new JSONObject(qsn);
                String qsn1 = array.getString("qsn");
                response = qsn1;//answerService.getAnswerById(8L);

                break;
            case "Fasthub":
           //     response = "Welcome to TakaTaka Collection Survey. Ready to initiate survey? \n1-Yes\n 2-No\n 0-quit? "+replyTo;
            case "1":
             //   response = "Do you live in dar es salaam? "+replyTo;
                break;
            case "2":
               // response = "Thank you for your time ";
                break;
            case "0":
                //surveyService.terminateSurvey();
                //response = "THANK YOU!";
                break;
            default:
                //response = "Invalid response";
                break;
        }
        List<MessageHandler> messages = new ArrayList<>();
            MessageHandler messageHandler = new MessageHandler(id,response,msisdn,"INFO");
            messages.add(messageHandler);

        Content content = new Content(Constant.channelLink, messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info(String.valueOf(content));
        HttpEntity<Content> entity = new HttpEntity<>(content,headers);
        log.info(String.valueOf(entity));
        Object responseMEssage= restTemplate.postForObject(Constant.GW_URL,entity,Object.class);
        log.info("response from gravity: " +responseMEssage);

        //return "redirect: /index";
        return new ResponseEntity(HttpStatus.OK);
    }

    ResponseEntity post(@RequestParam("id") String id,
                        @RequestParam("serviceNumber") String serviceNumber,
                        @RequestParam("text") String text,
                        @RequestParam("msisdn") String msisdn,
                        @RequestParam("date") String date,
                        @RequestParam("operator") String operator, String response){

        List<MessageHandler> messages = new ArrayList<>();
        MessageHandler messageHandler = new MessageHandler(id,response,msisdn,"INFO");
        messages.add(messageHandler);

        Content content = new Content(Constant.channelLink, messages);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        log.info(String.valueOf(content));
        HttpEntity<Content> entity = new HttpEntity<>(content,headers);
        log.info(String.valueOf(entity));
        Object responseMEssage= restTemplate.postForObject(Constant.GW_URL,entity,Object.class);
        log.info("response from gravity: " +responseMEssage);

        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

    public Survey[] initQsn(){
        return surveyService.initQsn();
    }

    @RequestMapping(method = RequestMethod.GET)
    public Survey[] getQuestions(){
        return surveyService.getQuestions();
    }
}