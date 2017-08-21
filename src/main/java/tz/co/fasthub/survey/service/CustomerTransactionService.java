package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.domain.Customer;
import tz.co.fasthub.survey.domain.CustomerTransaction;
import tz.co.fasthub.survey.domain.Question;

import java.util.List;

/**
 * Created by root on 7/25/17.
 */
public interface CustomerTransactionService {

    Iterable<CustomerTransaction> listAllCustomerTransactionByAttendedDesc(boolean attended);

    CustomerTransaction getOneTransactionByCustomerDesc(Customer customer, boolean attended);

    CustomerTransaction getCustomerTransactionById(Long id);

    CustomerTransaction saveCustomerTransaction(CustomerTransaction customerTransaction);

    void deleteCustomerTransaction(Long id);

    List<CustomerTransaction> getAllQuestionAndAnswer(Question question, Answer answer);


//    List<CustomerTransaction> getAllQuestionAndAnswerDetails(Question question, CustomerTransaction answerDetails);

    List<CustomerTransaction> getAnswerByQsnId(Question question);
}
