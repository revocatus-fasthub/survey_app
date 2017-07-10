package tz.co.fasthub.survey.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.controller.SurveyController;
import tz.co.fasthub.survey.domain.Survey;
import tz.co.fasthub.survey.service.SurveyService;

/**
 * Created by root on 6/20/17.
 */
@Service("surveyService")
public class SurveyServiceImpl implements SurveyService {

   // private final SurveyRepository surveyRepository;
    private static final Logger log = LoggerFactory.getLogger(SurveyController.class);
    private static AbstractApplicationContext context;
    private int answer;
/*
    @Autowired
    public SurveyServiceImpl(SurveyRepository surveyRepository) {
        this.surveyRepository = surveyRepository;
    }*/


    public Survey[] getQuestions(){
        Survey qsn1 = new Survey(1,"");//"Do you live in dar es salaam?"
        Survey qsn2 = new Survey(2,"Send 1 when you are ready");
        Survey quit = new Survey(0,"Thank you for your time");

        if(answer==1){
            return new Survey[]{qsn1};
        }else if(answer==2){
            return new Survey[]{qsn2};
        }else if(answer==0){
            terminateSurvey();
        }else {
            log.info("invalid response!");
        }

        return new Survey[]{qsn1,qsn2,quit};
    }

    public Survey[] initQsn(){
        Survey mainqsn = new Survey(0,"\\\\ready to initiate survey (1-Yes / 2-No / 0-quit):");
        return new Survey[]{mainqsn};
    }

    public void terminateSurvey() {
        log.info("Terminating Survey...");
        context.close();
    }

    @Override
    public Survey save(Survey survey) {
        return null;
    }
}
