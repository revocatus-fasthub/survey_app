package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Question;

import java.util.List;

/**
 * Created by naaminicharles on 6/20/17.
 */
public interface QuestionService {

    Question save(Question question);

    Question saveCommentByQsnId(Long id, String comment);

    Question getNextQuestion(Question currentCurrent);

    Question getPreviousQuestion(Question previousPrevious);

    Question getQsnById(Long id);

    Question getQnOneBySequence();

    List<Question> listAllQuestionsByDesc();

    List<Question> listAllQuestionsByAsc();

    List<Question> listAllQuestionsByStatus(String status);

    List<Question> listAllQuestionsByStatusAndIsChecked(String status, String isChecked);

    Question update(Question question);

    void deleteQuestion(Long id);

}

