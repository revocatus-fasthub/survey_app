package tz.co.fasthub.survey.repository;

import org.springframework.data.repository.CrudRepository;
import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.domain.Question;

import java.util.List;

/**
 * Created by root on 7/17/17.
 */
public interface AnswerRepository extends CrudRepository<Answer, Long> {

   List<Answer> findAllByQuestionOrderByPositionAsc(Question qsnId);
   List<Answer> findAllByQuestionOrderByPositionDesc(Question qsnId);
   Answer findAnswerByQuestionAndPosition(Question question, int position);
   Answer findAnswerByQuestion(Question question, String answer);

   Answer findByAns(Answer answer);

}