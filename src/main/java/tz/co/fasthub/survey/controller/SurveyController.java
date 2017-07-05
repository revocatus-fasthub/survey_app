package tz.co.fasthub.survey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tz.co.fasthub.survey.domain.Survey;
import tz.co.fasthub.survey.service.SurveyService;

/**
 * Created by root on 6/20/17.
 */
@RestController
@RequestMapping(value = "/sms/utc")
public class SurveyController {

    private SurveyMonkeyController surveyMonkeyController; // 15404

    private final SurveyService surveyService;
    private static final Logger log = LoggerFactory.getLogger(SurveyController.class);
    static AbstractApplicationContext context;

    @Autowired
    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @RequestMapping(params = {"id", "serviceNumber", "text", "msisdn", "date", "operator"}, method = RequestMethod.GET, produces = "text/plain")
    public ResponseEntity<String> index (@RequestParam("id") String id,
                                         @RequestParam("serviceNumber") String serviceNumber,
                                         @RequestParam("text") String text,
                                         @RequestParam("msisdn") String msisdn,
                                         @RequestParam("date") String date,
                                         @RequestParam("operator") String operator) {

        final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.TEXT_PLAIN);
        httpHeaders.add("id",id);
        log.info("received message from gravity: "+text+ " from "+msisdn);
        String response = null;

        switch (text) {
            case "FastHub":
                response = surveyMonkeyController.getQsn1();//"Ready to initiate survey (\n1-Yes \n 2-No \n 0-quit)?";
                break;
            case "1":
                response="Do you live in dar es salaam?";
                break;
            case "2":
                response="Thank you for your time";
                break;
            case "0":
                surveyService.terminateSurvey();
                response="THANK YOU!";
                break;
            default:
                response="Invalid response";
                break;
        }
        return new ResponseEntity<String>(response, httpHeaders, HttpStatus.OK);
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
