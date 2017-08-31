package tz.co.fasthub.survey.repository;

import org.springframework.data.repository.CrudRepository;
import tz.co.fasthub.survey.domain.Question;

import java.util.List;

/**
 * Created by naaminicharles on 7/17/17.
 */
public interface QuestionRepository extends CrudRepository<Question, Long> {

    List<Question> findAllByOrderBySequenceAsc();
    List<Question> findAllByOrderBySequenceDesc();

    List<Question> findAllByStatus(String status);
//    List<Question> findAllByOrderBySequenceAndStatus(int sequence, String status);


    Question findAllByOrderByIdDesc(int id);
    Question findAllByOrderByTypeDesc(Question type);


}

