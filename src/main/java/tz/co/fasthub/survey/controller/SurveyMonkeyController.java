package tz.co.fasthub.survey.controller;

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
import java.util.Map;

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
    static String access_token;
    private String code = null;
    private static final String complete_link="https://www.surveymonkey.com/oauth/authorize?response_type=code&redirect_uri="+redirect_uri+"&client_id="+client_id;

    private static final Logger log = LoggerFactory.getLogger(SurveyMonkeyController.class);

    private static final String access_authorized="https://api.surveymonkey.com/api_console/oauth2callback?code=";
    private static final String requestTokenUrl = "https://api.surveymonkey.net/oauth/token";
    private static final String url="https://api.surveymonkey.net/v3/surveys/QKN5GNB/collectors";


    private static final String fetchSurvey=" /surveys/118875579/responses/bulk";
    private static final String viewSurvey="https://api.surveymonkey.net/v3/surveys";


    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/authorizationUrl")
    public String connectSurvey(){
        return "redirect:"+complete_link;
    }

    private String doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        HttpSession session = request.getSession();
        //code=response.getHeader("code");
        code=request.getParameter("code");
        log.info("code = "+code);
    return "redirect:"+access_authorized+code;
    }

    @RequestMapping("/callback")
    public String afterAuthorization(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                doGet(request,response);
             //   authorize(request);
                requestToken(request,response);
                return "index";
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }

        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> requestToken(HttpServletRequest request, HttpServletResponse response){
        Object payload;
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
        //log.info("access token = "+accesToken);
        //log.info(request.getHeader("bearer "));
        HttpEntity<?> entity = new HttpEntity<Object>(parts, headers);
        log.info(String.valueOf(parts));

        payload = restTemplate.postForObject(requestTokenUrl, entity, Object.class);

        log.info("payload: " +payload);
        return new ResponseEntity<String>("response", headers, HttpStatus.OK);
    }



}
