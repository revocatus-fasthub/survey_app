package tz.co.fasthub.survey.repository;

import org.springframework.data.repository.CrudRepository;
import tz.co.fasthub.survey.domain.Question;

/**
 * Created by root on 7/17/17.
 */
public interface QuestionRepository extends CrudRepository<Question, Long> {

    public Question findAllByOrderByIdAsc();
    public Question findAllByOrderByIdDesc(int id);

}

