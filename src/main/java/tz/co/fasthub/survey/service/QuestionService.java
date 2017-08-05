package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Question;

import java.util.List;

/**
 * Created by root on 6/20/17.
 */
public interface QuestionService {

    Question save(Question question);

    Question getNextQuestion(Question currentCurrent);

    Question getQsnById(Long id);

    Question getQnOneBySequence();

    List<Question> listAllQuestionsByDesc();

    List<Question> listAllQuestionsByAsc();

    Question update(Question question);

    void deleteQuestion(Long id);

}

