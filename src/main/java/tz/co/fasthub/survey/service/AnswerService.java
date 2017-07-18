package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.domain.Question;

/**
 * Created by root on 6/20/17.
 */
public interface AnswerService {
    
    Answer save(Answer answer, Long id);
    Answer getAnswerById(Long id);
    Answer saveByQnsId(Answer ans, Question qsn);


    Iterable<Answer> listAllAnswers();
}
