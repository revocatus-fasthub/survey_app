package tz.co.fasthub.survey.repository;

import org.springframework.data.repository.CrudRepository;
import tz.co.fasthub.survey.domain.Answer;

/**
 * Created by root on 7/17/17.
 */
public interface AnswerRepository extends CrudRepository<Answer, Long> {
  //  List<Answer> findByAnswerByQuestionId(Long qsnId);
}
