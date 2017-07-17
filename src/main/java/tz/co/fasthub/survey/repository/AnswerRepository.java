package tz.co.fasthub.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.co.fasthub.survey.domain.Answer;

/**
 * Created by root on 7/17/17.
 */
public interface AnswerRepository extends JpaRepository<Answer, Long>{
}
