package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Customer;
import tz.co.fasthub.survey.domain.OpenEndedAnswer;
import tz.co.fasthub.survey.domain.Question;

/**
 * Created by root on 6/20/17.
 */
public interface OpenAnswerService {
    
    OpenEndedAnswer save(OpenEndedAnswer answer);

    OpenEndedAnswer getAnswerByQsnId(Question qsnId);

    OpenEndedAnswer getOneTransactionByCustomerDesc(Customer customer);

    OpenEndedAnswer getAllByQuestion(Question question);
}
