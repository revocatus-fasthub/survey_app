package tz.co.fasthub.survey.repository;

import org.springframework.data.repository.CrudRepository;
import tz.co.fasthub.survey.domain.Customer;
import tz.co.fasthub.survey.domain.OpenEndedAnswer;
import tz.co.fasthub.survey.domain.Question;

import java.util.List;

/**
 * Created by root on 7/17/17.
 */
public interface OpenAnswerRepository extends CrudRepository<OpenEndedAnswer, Long> {

    List<OpenEndedAnswer> findAllByCustomerOrderByIdDesc(Customer customer);

    OpenEndedAnswer findByQuestion(Question question);

}