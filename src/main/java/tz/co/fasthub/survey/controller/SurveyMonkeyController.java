package tz.co.fasthub.survey.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by root on 6/22/17.
 */
@Controller
@RequestMapping("/survey")
public class SurveyMonkeyController {
//http://survey.fasthub.co.tz:8081

//id=118875579

    private static final String oauthLink ="https://api.surveymonkey.net/oauth/authorize?response_type=code&redirect_uri=";
    private static final String redirect_uri="http://127.0.0.1:8081/survey/callback";//http://127.0.0.1:8081/survey/callback  http://survey.fasthub.co.tz:8081/survey/callback https://www.surveymonkey.com
    private static final String client_id = "g5qjzImSTsuaBTr3JcMUOw";
    private static final String client_secret="225873409898429840227397389705343753331";
    private static final String grant_type = "authorization_code";
    private static String access_token;
    private static String accessTokenFromPayload;
    private String code = null;
    private static final String complete_link="https://www.surveymonkey.com/oauth/authorize?response_type=code&redirect_uri="+redirect_uri+"&client_id="+client_id;

    private static final Logger log = LoggerFactory.getLogger(SurveyMonkeyController.class);

    private static final String access_authorized="https://api.surveymonkey.com/api_console/oauth2callback?code=";
    private static final String requestTokenUrl = "https://api.surveymonkey.net/oauth/token";


    private static final String collectorUrl="https://api.surveymonkey.net/v3/surveys/118875579/collectors";//https://api.surveymonkey.net/v3/surveys/118875579/collectors
    private static final String viewSurveyUrl="https://api.surveymonkey.net/v3/surveys/118875579/details";
    private static final String responseUrl="https://api.surveymonkey.net/v3/collectors/158591453/responses";
    private static final String viewQuestionsUrl="https://api.surveymonkey.net/v3/surveys/118875579/pages/118875579/questions";
    private static final String fetchSurvey=" /surveys/118875579/responses/bulk";
    private static final String viewSurvey="https://api.surveymonkey.net/v3/surveys/118875579";


    private RestTemplate restTemplate = new RestTemplate();
    private String at = "apzqGssqUXFDD35T4S1gtUz5IbICohJcOUwmzzcJrifSWQWXbk2qTFK9ULRYi1KBe8yIDjMYsN8-5P5vVToFnneWxfM39fV7oDsRvw1mL-B-afsvFdcB.xnSxE6z0m-a";

    @RequestMapping(value = "/authorizationUrl")
    public String connectSurvey(){
        return "redirect:"+complete_link;
    }

    private String doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        HttpSession session = request.getSession();
        code=request.getParameter("code");
        log.info("code = "+code);
    return "redirect:"+access_authorized+code;
    }

    @RequestMapping("/callback")
    public String afterAuthorization(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                doGet(request,response);
                requestToken(request,response);
                return "index";
                //return "redirect:/survey/viewCollector";
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> requestToken(HttpServletRequest request, HttpServletResponse response) throws JSONException {

        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
            parts.add("client_id",client_id);
            parts.add("client_secret", client_secret);
            parts.add("redirect_uri",redirect_uri);
            parts.add("code", code);
            parts.add("grant_type",grant_type);
        request.getSession();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("bearer ","Authorization: bearer " +access_token);
        HttpEntity<?> entity = new HttpEntity<Object>(parts, headers);
        log.info(String.valueOf(parts));

        Object payload = restTemplate.postForObject(requestTokenUrl, entity, Object.class);
        log.info("payload: " + payload);

        String jsonStr = String.valueOf(payload);
        JSONObject jsonObject = new JSONObject(jsonStr);
        accessTokenFromPayload  = jsonObject.getString("access_token");
        log.info("Access token = "+accessTokenFromPayload);
        //return "success";
        return new ResponseEntity<>("response", headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/viewCollector",method = RequestMethod.GET)
    public String viewCollectors(HttpServletRequest request){
        request.getSession();
        log.info("**********************"+accessTokenFromPayload);
         MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
       //   parts.add("type","weblink");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","bearer "+accessTokenFromPayload);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(collectorUrl,HttpMethod.GET,entity,String.class);
        response.getBody();
        log.info("response:" +response);

        return "success";
    }


    @RequestMapping(value = "/viewSurvey",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> view(){
        log.info("**********************"+accessTokenFromPayload);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","bearer "+accessTokenFromPayload);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(viewSurveyUrl,HttpMethod.GET,entity,String.class);
        response.getBody();
        log.info("response: "+response);

        return new ResponseEntity<>("can view survey", headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/viewResponses",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> viewResponses(HttpServletRequest request){
        request.getSession();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","bearer "+accessTokenFromPayload);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(responseUrl,HttpMethod.GET,entity,String.class);
        response.getBody();
        log.info("response: "+response);

        return new ResponseEntity<>("can view response", headers, HttpStatus.OK);

    }

/*    @RequestMapping(value = "/viewQuestions",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> viewQuestions(HttpServletRequest request){
        request.getSession();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","bearer "+accessTokenFromPayload);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(viewQuestionsUrl,HttpMethod.GET,entity,String.class);
        response.getBody();
        log.info("response: "+response);

        return new ResponseEntity<>("can view questions", headers, HttpStatus.OK);

    }*/


}