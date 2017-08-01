package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Customer;
import tz.co.fasthub.survey.domain.OpenEndedAnswer;
import tz.co.fasthub.survey.domain.Question;

/**
 * Created by root on 6/20/17.
 */
public interface OpenAnswerService {
    
    OpenEndedAnswer save(OpenEndedAnswer answer);

    OpenEndedAnswer getAnswerById(Long id);


    OpenEndedAnswer getAnswerByQsnId(Question qsnId);


    OpenEndedAnswer saveByQnsId(OpenEndedAnswer answer, Question qsnId);

    OpenEndedAnswer getOneTransactionByCustomerDesc(Customer customer);

    Iterable<OpenEndedAnswer> getAnswerByQsnIdAll(Long id);

    Iterable<OpenEndedAnswer> listAllAnswers();

    OpenEndedAnswer getAllByQuestion(Question question);
}
