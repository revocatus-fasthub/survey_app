package tz.co.fasthub.survey.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import tz.co.fasthub.survey.domain.Survey;
import tz.co.fasthub.survey.service.SurveyService;

import javax.websocket.ClientEndpoint;

/**
 * Created by root on 6/20/17.
 */
@ClientEndpoint
public class RequestController {

    @Autowired
    SurveyService surveyService;

    public @ResponseBody SurveyResponse getSurvey(){
        return buildResponse();
    }

    private SurveyResponse buildResponse(){
        SurveyResponse surveyResponse = new SurveyResponse();
        for (Survey survey: surveyService.getQuestions()){
            surveyResponse.getSurvey();
        }
        return surveyResponse;
    }
}
