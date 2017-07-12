package tz.co.fasthub.survey.controller;

import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import tz.co.fasthub.survey.constants.Constant;
import tz.co.fasthub.survey.domain.Channel;
import tz.co.fasthub.survey.domain.Content;
import tz.co.fasthub.survey.domain.MessageHandler;
import tz.co.fasthub.survey.domain.Survey;
import tz.co.fasthub.survey.service.SurveyService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 6/20/17.
 */
@RestController
@RequestMapping(value = "/sms/utc")
public class SurveyController {
//http://survey.fasthub.co.tz:8081/sms/utc?id=74&serviceNumber=665656&text=Test&msisdn=254791199624&date=2016-05-18&operator=safaricom-soap
    //http://127.0.0.1:8081/sms/utc?id=74&serviceNumber=0785723360&text=FastHub&msisdn=255754088816&date=2016-05-18&operator=safaricom-soap
    @Autowired
    private SurveyMonkeyController surveyMonkeyController;

    RestTemplate restTemplate = new RestTemplate();

    private Channel channelLink = new Channel(Constant.channel, Constant.password);

    private Content content;

    String message = "Welcome to TakaTaka Collection Survey \\n1-Yes \\n 2-No \\n 0-quit?";//"Ready to initiate survey \n1-Yes \n 2-No \n 0-quit?";

    private final SurveyService surveyService;
    private static final Logger log = LoggerFactory.getLogger(SurveyController.class);
    static AbstractApplicationContext context;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @RequestMapping(params = {"id", "serviceNumber", "text", "msisdn", "date", "operator"}, method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity index (@RequestParam("id") String id,
                                 @RequestParam("serviceNumber") String serviceNumber,
                                 @RequestParam("text") String text,
                                 @RequestParam("msisdn") String msisdn,
                                 @RequestParam("date") String date,
                                 @RequestParam("operator") String operator) throws JSONException {

       /* final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
      */
        log.info("received message from gravity: "+text+ " from "+msisdn);
        String response;
        String replyTo="\nSend your response to : 0785723360";

        switch (text) {
            case "FastHub":
                response = surveyMonkeyController.getQsnOne()+"\n"+replyTo;
                break;
            case "Fasthub":
                response = "Welcome to TakaTaka Collection Survey \n1-Yes\n 2-No\n 0-quit? "+replyTo;
            case "1":
                response = "Do you live in dar es salaam? "+replyTo;
                break;
            case "2":
                response = "Thank you for your time ";
                break;
            case "0":
                //surveyService.terminateSurvey();
                response = "THANK YOU!";
                break;
            default:
                response = "Invalid response";
                break;
        }
        List<MessageHandler> messages = new ArrayList<>();
            MessageHandler messageHandler = new MessageHandler(id,response,msisdn,"INFO");
            messages.add(messageHandler);

        content = new Content(channelLink,messages);

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