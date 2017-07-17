package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Question;

/**
 * Created by root on 6/20/17.
 */
public interface QuestionService {

    Question save(Question question);
    Question getQsnById(Long id);
    Question getQsnByAscendingId(int sequence);
    Question getQsnByDescendingId(int sequence);
    Question getQnsBySequence(Integer id);
    Iterable<Question> listAllTalent();
}
