package tz.co.fasthub.survey.controller;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import tz.co.fasthub.survey.domain.Contact;
import tz.co.fasthub.survey.domain.ContactHandler;
import tz.co.fasthub.survey.domain.Payload;
import tz.co.fasthub.survey.service.ContactService;
import tz.co.fasthub.survey.service.PayloadService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import static tz.co.fasthub.survey.constants.Constant.*;

  /**
 * Created by naaminicharles on 6/22/17.
 */
@Controller
@RequestMapping("/survey")
public class SurveyMonkeyController {

    @Autowired
    private SurveyController surveyController;

    @Autowired
    private ContactService contactService;

    @Autowired
    private PayloadService payloadService;

    private static final Logger log = LoggerFactory.getLogger(SurveyMonkeyController.class);

      private Payload payload = new Payload(access_token,expires_in,token_type);

    private Contact contact = new Contact(href,first_name,last_name,contactId,email,phoneNumber);

    private RestTemplate restTemplate = new RestTemplate();

    @RequestMapping(value = "/authorizationUrl")
    public String connectSurvey(){
        return "redirect:"+complete_link;
    }

    @RequestMapping(value = "/surveyQuestions")
    public String surveyQuestions(){
          return "surveyQuestions";
      }



    @RequestMapping(value = "/successPage")
    public String successPage(){
        return "viewSurvey";
    }

    private String doGet(HttpServletRequest request) throws javax.servlet.ServletException, IOException {
        HttpSession session = request.getSession();
        code=request.getParameter("code");
        log.info("code = "+code);
    return "redirect:"+access_authorized+code;
    }

    @RequestMapping("/callback")
    public String afterAuthorization(HttpServletRequest request) throws ServletException, IOException {
            try {
                doGet(request);
                requestToken(request);
               // return "index";
                return "redirect:/survey/index";
            } catch (ServletException | IOException | JSONException e) {
                e.printStackTrace();
            }

        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String requestToken(HttpServletRequest request) throws JSONException {

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

            accessTokenFromPayload = jsonObject.getString("access_token");
            token_type = jsonObject.getString("token_type");
            expires_in = jsonObject.getString("expires_in");

            savingPayloadToDb();

        }
        return "redirect:/survey/successPage";

    }

    private void savingPayloadToDb(){
        try {
            payload.setAccess_token(accessTokenFromPayload);
            payload.setExpires_in(expires_in);
            payload.setToken_type(token_type);

            payloadService.save(payload);

        } catch (Exception e) {
            log.info("error... : " + e.getMessage());
        }
    }

    @RequestMapping(value = "/viewCollector/{id}",method = RequestMethod.GET)
    public String viewCollectors(@PathVariable Long id, HttpServletRequest request){
        id= 1L;
        request.getSession();
        Payload createdPayload = payloadService.getPayloadById(id);
        log.info("********************** "+ createdPayload.getAccess_token());
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("type","weblink");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            headers.add("Authorization", "bearer " + createdPayload.getAccess_token());

            HttpEntity<?> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(collectorUrl, HttpMethod.GET, entity, String.class);
            response.getBody();
            log.info("response:" + response);
        }catch (Exception ex){
            log.info("Error: "+ex.getMessage());
        }

        return "redirect:/survey/successPage";
        //return new ResponseEntity<>("response", headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/viewSurvey/{id}",method = RequestMethod.GET, produces = "application/json")
    public String view(@PathVariable Long id){
        id= 1L;
        Payload createdPayload = payloadService.getPayloadById(id);
        log.info("*********************"+createdPayload.getAccess_token());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","bearer "+createdPayload.getAccess_token());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(viewSurveyUrl,HttpMethod.GET,entity,String.class);
        response.getBody();
        log.info("response: "+response);

        return "redirect:/survey/successPage";
    }

    @RequestMapping(value = "/viewResponses/{id}",method = RequestMethod.GET, produces = "application/json")
    public String viewResponses(HttpServletRequest request){
        Long id = 1L;
        Payload createdPayload = payloadService.getPayloadById(id);
        request.getSession();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","bearer "+createdPayload.getAccess_token());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(responseUrl,HttpMethod.GET,entity,String.class);
        response.getBody();
        log.info("response: "+response);
        return "redirect:/survey/successPage";

    }

    @RequestMapping(value = "/viewQuestions/{id}",method = RequestMethod.GET, produces = "application/json")
    public String getQuestions(){
        Long id = 1L;
        Payload createdPayload = payloadService.getPayloadById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","bearer "+createdPayload.getAccess_token());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(viewQuestionsUrl,HttpMethod.GET,entity,String.class);
        response.getBody();
        log.info("response: "+response);

        return "redirect:/survey/successPage";
        //return new ResponseEntity<>("can view questions", headers, HttpStatus.OK);

    }

    @RequestMapping(value = "/qsnOne/{id}",method = RequestMethod.GET, produces = "application/json")
    String getQsnOne() throws JSONException {
        Long id = 1L;
        Payload createdPayload = payloadService.getPayloadById(id);
        log.info("*******************"+createdPayload.getAccess_token());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","bearer "+createdPayload.getAccess_token());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response1 = restTemplate.exchange(qsnONe,HttpMethod.GET,entity,String.class);
        response1.getBody();
        log.info("response: "+response1);

        String jsonStr1 = String.valueOf(response1);
        final ArrayList<String> choiceList = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonStr1.substring(jsonStr1.indexOf('{')));

        JSONObject answers = new JSONObject(jsonObject.getString("answers"));
        JSONArray choices = answers.getJSONArray("choices");
        JSONArray jsonArray=jsonObject.getJSONArray("headings");

        String choice = null, position=null, heading = null;
        for(int y = 0;y<choices.length();y++){
            String list=null;
            JSONObject innerObj = choices.getJSONObject(y);
            choice = innerObj.getString("text");
            position = innerObj.getString("position");
            list = position+ ". "+choice;
            log.info(list);
            choiceList.add(list);
        }

        for(int i = 0; i<jsonArray.length(); i++){
            JSONObject innerObj = jsonArray.getJSONObject(i);
            heading = innerObj.getString("heading");
            log.info("heading= "+heading);
        }
    return heading+"\n" +listCleanUp(choiceList);
    }

    public String listCleanUp(ArrayList<String> listing){
        StringBuilder sb = new StringBuilder();
        for (String str :
                listing) {

            sb.append(str);
            if (listing.indexOf(str)!= listing.size()){
             sb.append("\n");
            }

        };
        return sb.toString();
    }

    @RequestMapping(value = "/loopQsns/{id}",method = RequestMethod.GET, produces = "application/json")
    public String loopQsns() throws JSONException {
        Long id = 1L;
        Payload createdPayload = payloadService.getPayloadById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","bearer "+createdPayload.getAccess_token());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        //fetch qsns from survey monkey
        ResponseEntity<String> response1 = restTemplate.exchange(viewQuestionsUrl,HttpMethod.GET,entity,String.class);
        //get question from array
        String jsonStr1 = String.valueOf(response1);

        String list=null, position=null, heading=null;
        JSONObject jsonObject = new JSONObject(jsonStr1.substring(jsonStr1.indexOf('{')));
        JSONArray choices = jsonObject.getJSONArray("data");

        for(int y = 0;y<choices.length();y++) {

            JSONObject innerObj = choices.getJSONObject(y);
            position = innerObj.getString("position");
            heading = innerObj.getString("heading");

            list = position+ ". "+heading;
            qsnList.add(list);
            log.info("QsnList: "+listCleanUp(qsnList));
        }
        return listCleanUp(qsnList);
        }

      @RequestMapping(value = "/allContactList/{id}",method = RequestMethod.GET)
      public String getContactsList(){
          Long id = 1L;
          Payload createdPayload = payloadService.getPayloadById(id);

          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.APPLICATION_JSON);
          headers.add("Authorization","bearer "+createdPayload.getAccess_token());

          HttpEntity<?> entity = new HttpEntity<>(headers);

          ResponseEntity<String> contacts = restTemplate.exchange(allContactList, HttpMethod.GET, entity, String.class);
          contacts.getBody();
          log.info("response:" + contacts);

          return "redirect:/survey/successPage";
      }

      @RequestMapping(value = "/getAllContact/{id}",method = RequestMethod.GET)
      public String getAllContact() throws JSONException {
          Long id = 1L;
          Payload createdPayload = payloadService.getPayloadById(id);
          log.info("get all contacts: \n");
          HttpHeaders headers = new HttpHeaders();
          headers.setContentType(MediaType.APPLICATION_JSON);
          headers.add("Authorization","bearer "+createdPayload.getAccess_token());

          HttpEntity<?> entity = new HttpEntity<>(headers);

          ResponseEntity<ContactHandler> contactResponse = restTemplate.exchange(allContacts,HttpMethod.GET,entity, ContactHandler.class);
          ContactHandler handler = contactResponse.getBody();
          log.info("response:" + contactResponse);
          contactService.save(handler.getData());

          return "redirect:/survey/successPage";
      }
  }