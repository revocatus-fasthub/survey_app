package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Survey;

/**
 * Created by root on 6/20/17.
 */
public interface SurveyService {
    Survey[] getQuestions();
    Survey[] initQsn();
    void terminateSurvey();
    Survey save(Survey survey);
}
