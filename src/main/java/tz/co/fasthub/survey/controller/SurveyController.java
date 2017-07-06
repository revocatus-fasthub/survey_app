package tz.co.fasthub.survey.controller;

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
import tz.co.fasthub.survey.domain.Survey;
import tz.co.fasthub.survey.service.SurveyService;

/**
 * Created by root on 6/20/17.
 */
@RestController
@RequestMapping(value = "/sms/utc")
public class SurveyController {

    private SurveyMonkeyController surveyMonkeyController;

    RestTemplate restTemplate = new RestTemplate();

    private Channel channelLink = new Channel(Constant.channel, Constant.password);

    private Content content;

    private final SurveyService surveyService;
    private static final Logger log = LoggerFactory.getLogger(SurveyController.class);
    static AbstractApplicationContext context;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @RequestMapping(params = {"id", "serviceNumber", "text", "msisdn", "date", "operator"}, method = RequestMethod.GET, produces = "text/plain")
    public String index (@RequestParam("id") String id,
                                         @RequestParam("serviceNumber") String serviceNumber,
                                         @RequestParam("text") String text,
                                         @RequestParam("msisdn") String msisdn,
                                         @RequestParam("date") String date,
                                         @RequestParam("operator") String operator) {

       /* final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
      */
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.add("id",id);

        log.info("received message from gravity: "+text+ " from "+msisdn);
        String response;

        switch (text) {
            case "FastHub"://surveyMonkeyController.getQsnOne();
                String message = "Ready to initiate survey \n1-Yes \n 2-No \n 0-quit?";
                response = message;
                break;
            case "1":
                response="Do you live in dar es salaam?";
                break;
            case "2":
                response="Thank you for your time";
                break;
            case "0":
                //surveyService.terminateSurvey();
                response="THANK YOU!";
                break;
            default:
                response="Invalid response";
                break;
        }
        content = new Content(channelLink,response);

        HttpEntity<Content> entity = new HttpEntity<>(content,headers);

        Object responseMEssage= restTemplate.postForObject(Constant.GW_URL,entity,Object.class);
        log.info("response from gravity: " +responseMEssage);

        return "redirect: /index";
        //return new ResponseEntity<String>(response, httpHeaders, HttpStatus.OK);
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
