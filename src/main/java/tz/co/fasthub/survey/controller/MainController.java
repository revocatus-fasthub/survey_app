package tz.co.fasthub.survey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by root on 7/10/17.
 */
@Controller
public class MainController {
    @RequestMapping(value = "/survey/viewController")
    public String viewController(){
        return "/survey/viewController";
    }

    @RequestMapping(value = "/surveyQuestions")
    public String surveyQuestions(){
        return "surveyQuestions";
    }

}
