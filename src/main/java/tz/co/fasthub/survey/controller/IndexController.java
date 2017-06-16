package tz.co.fasthub.survey.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/sms/utc")
public class IndexController{

    @RequestMapping(params = {"id", "serviceNumber", "text", "msisdn", "date", "operator"})
    public String index (@RequestParam("id") String id,
                         @RequestParam("serviceNumber") String serviceNumber,
                         @RequestParam("text") String text,
                         @RequestParam("msisdn") String msisdn,
                         @RequestParam("date") String date,
                         @RequestParam("operator") String operator, Model map) {

        map.addAttribute("msg", "Your Details Are : " + id +
                ", " + serviceNumber +", " + text +", " + msisdn +", " + date +", " + operator);

        return "index";
    }
}

//http://localhost:8080/sms/utc?id=74&serviceNumber=665656&text=Test&msisdn=254791199624&date=2016-05-18&operator=safaricom-soap