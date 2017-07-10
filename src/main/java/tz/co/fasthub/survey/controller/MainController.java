package tz.co.fasthub.survey.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by root on 7/10/17.
 */
public class MainController {
    @RequestMapping(value = "/survey/viewController")
    public String viewController(){
        return "/survey/viewController";
    }

}
