package tz.co.fasthub.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.domain.Question;
import tz.co.fasthub.survey.repository.AnswerRepository;
import tz.co.fasthub.survey.service.AnswerService;
import tz.co.fasthub.survey.service.QuestionService;

/**
 * Created by root on 7/17/17.
 */
@Service("answerService")
public class AnswerServiceImpl implements AnswerService {
    
    private final AnswerRepository answerRepository;

    private final Answer answer = new Answer();

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private final QuestionService questionService;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    @Override
    public Answer save(Answer answer, Long id) {
        answer.setQuestion(questionService.getQsnById(id));
        return answerRepository.save(answer);
    }

    @Override
    public Answer getAnswerById(Long id) {
        return answerRepository.findOne(id);
     }

    @Override
    public Answer saveByQnsId(Answer ans, Question qsn) {
       //  questionService.getQsnById(qsn.getId());
        return  answerRepository.save(ans);
    }

    @Override
    public Iterable<Answer> getAnswerByQsnId(Long id) {


        questionService.getQsnById(id);
        return answerRepository.findAll();
    }

    @Override
    public Iterable<Answer> listAllAnswers() {
        return answerRepository.findAll();
    }

}
