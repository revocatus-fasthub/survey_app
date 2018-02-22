package tz.co.fasthub.survey.repository;

import org.springframework.data.repository.CrudRepository;
import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.domain.Customer;
import tz.co.fasthub.survey.domain.CustomerTransaction;
import tz.co.fasthub.survey.domain.Question;

import java.util.List;

/**
 * Created by naaminicharles on 7/25/17.
 */
public interface CustomerTransactionRepository extends CrudRepository<CustomerTransaction ,Long>{
    List<CustomerTransaction> findAllByCustomerAndAttendedOrderByIdDesc(Customer customer, boolean attended);
    List<CustomerTransaction> findByAttendedOrderByIdDesc(boolean attended);

    List<CustomerTransaction> findAllByQuestionAndAnswer(Question question, Answer answer);

//    List<CustomerTransaction> findAllByQuestionAndAnswerDetails(Question question, CustomerTransaction answerDetails);

    List<CustomerTransaction> findAllByQuestion(Question question);

    List<CustomerTransaction> findAllByCustomer(String customerTransaction);

    List<CustomerTransaction> findByIdGreaterThan(Long id);

    List<CustomerTransaction> findAllByAnswerDetailsContainingIgnoreCaseOrCustomerMsisdnContainingOrQuestionQsnContainingIgnoreCaseOrAnswerAnsContainingIgnoreCase(String answerDetails,String msisdnQuery, String qsnQuery, String ansQuery);
}
