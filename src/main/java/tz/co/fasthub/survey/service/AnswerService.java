package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.domain.Question;

import java.util.List;

/**
 * Created by root on 6/20/17.
 */
public interface AnswerService {
    
    Answer save(Answer answer);
    Answer getAnswerById(Long id);
    Answer saveByQnsId(Answer answer, Question qsnId);
    List<Answer> getAnswerByQsnId(Question qsnId);
    Iterable<Answer> listAllAnswers();
    List<Answer> listAllAnswersByDesc(Question qsnId);
    String getAnswerByQuestion(Question question);
    Answer getAllByQuestionAndPosition(Question question, int position);

    Iterable<Answer> getAnswerByQsnIdAll(Long id);
    void deleteAnswer(Long id);

}
