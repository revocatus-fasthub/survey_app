package tz.co.fasthub.survey.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import tz.co.fasthub.survey.domain.Payload;
import tz.co.fasthub.survey.service.PayloadService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static tz.co.fasthub.survey.constants.Constant.*;

/**
 * Created by root on 6/22/17.
 */
@Controller
@RequestMapping("/survey")
public class SurveyMonkeyController {
//http://survey.fasthub.co.tz:8081

//survey_id=118875579
    private Payload payload;

    @Autowired
    PayloadService payloadService;

    private static final Logger log = LoggerFactory.getLogger(SurveyMonkeyController.class);

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/authorizationUrl")
    public String connectSurvey(){
        return "redirect:"+complete_link;
    }

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }

    @RequestMapping(value = "/successPage")
    public String successPage(){
        return "viewSurvey";
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
    public String requestToken(HttpServletRequest request, HttpServletResponse response) throws JSONException {

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

        Payload payloading = restTemplate.postForObject(requestTokenUrl, entity, Payload.class);
        log.info("payload: " + payloading);

        if(payloading!=null) {
            JSONObject jsonObject = new JSONObject(payloading);
            // log.info(String.valueOf(jsonStr.trim()));
            //get items from json
            accessTokenFromPayload = jsonObject.getString("access_token");
            token_type = jsonObject.getString("token_type");
            expires_in = jsonObject.getString("expires_in");

            log.info("Access token = "+accessTokenFromPayload);
            log.info("token_type = "+token_type);
            log.info("expires_in = "+expires_in);

            //save payload to db
            try {
                payload.setAccess_token(accessTokenFromPayload);
                payload.setExpires_in(token_type);
                payload.setToken_type(expires_in);
                payloadService.save(payload);

            } catch (Exception e) {
                log.info("error... : " + e.getMessage());
                return "redirect:/survey/successPage";
            }
        }
        return "redirect:/survey/successPage";
        //return new ResponseEntity<>("response", headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/viewCollector",method = RequestMethod.GET)
    public String viewCollectors(HttpServletRequest request){
        request.getSession();
        log.info("**********************"+ accessTokenFromPayload);
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("type","weblink");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //headers.add("Authorization ","bearer "+accessTokenFromPayload);
        headers.add("Authorization","bearer "+ accessTokenFromPayload);

        HttpEntity<?> entity = new HttpEntity<>(headers);


        //Object collectors = restTemplate.getForObject(collectorUrl,Object.class,entity);
        ResponseEntity<String> response = restTemplate.exchange(collectorUrl,HttpMethod.GET,entity,String.class);
        response.getBody();
        log.info("response:" +response);
        //log.info("collectors= "+collectors);

        return "redirect:/survey/successPage";
        //return new ResponseEntity<>("response", headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/viewSurvey",method = RequestMethod.GET, produces = "application/json")
    public String view(){
        log.info("*********************"+accessTokenFromPayload);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","bearer "+accessTokenFromPayload);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(viewSurveyUrl,HttpMethod.GET,entity,String.class);
        response.getBody();
        log.info("response: "+response);

        return "redirect:/survey/successPage";
      //  return new ResponseEntity<>("can view survey", headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/viewResponses",method = RequestMethod.GET, produces = "application/json")
    public String viewResponses(HttpServletRequest request){
        request.getSession();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","bearer "+accessTokenFromPayload);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(responseUrl,HttpMethod.GET,entity,String.class);
        response.getBody();
        log.info("response: "+response);
        return "redirect:/survey/successPage";
        //return new ResponseEntity<>("can view response", headers, HttpStatus.OK);

    }

    @RequestMapping(value = "/viewQuestions",method = RequestMethod.GET, produces = "application/json")
    public String viewQuestions(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","bearer "+accessTokenFromPayload);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(viewQuestionsUrl,HttpMethod.GET,entity,String.class);
        response.getBody();
        log.info("response: "+response);

        return "redirect:/survey/successPage";
        //return new ResponseEntity<>("can view questions", headers, HttpStatus.OK);

    }

    @RequestMapping(value = "/qsnOne",method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> getQsnOne() throws JSONException {
        log.info("*******************"+accessTokenFromPayload);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","bearer "+accessTokenFromPayload);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response1 = restTemplate.exchange(qsnONe,HttpMethod.GET,entity,String.class);
        response1.getBody();
        log.info("response: "+response1);

        String jsonStr1 = String.valueOf(response1);

        JSONObject jsonObject = new JSONObject(jsonStr1);
        String question1 = jsonObject.getString("heading");
        log.info("question 1 states:" +question1);


        // return "redirect:/survey/successPage";
        return new ResponseEntity<>(question1, headers, HttpStatus.OK);

    }

}
