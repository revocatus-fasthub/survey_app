package tz.co.fasthub.survey.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/sms/utc")
public class IndexController{

    private static final Logger log = LoggerFactory.getLogger(IndexController.class);


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

        return new ResponseEntity<String>("THANK YOU", httpHeaders, HttpStatus.OK);
    }

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }


}

//http://localhost:8080/sms/utc?id=74&serviceNumber=665656&text=Test&msisdn=254791199624&date=2016-05-18&operator=safaricom-soap
//map.addAttribute("msg", "Your Details Are : " + id + ", " + serviceNumber +", " + text +", " + msisdn +", " + date +", " + operator);
