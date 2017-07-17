package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Answer;

/**
 * Created by root on 6/20/17.
 */
public interface AnswerService {
    
    Answer save(Answer answer);
    Answer getAnswerById(Long id);
    Answer saveByQnsId(Answer ans, Long qsnId);


    Iterable<Answer> listAllAnswers();
}
