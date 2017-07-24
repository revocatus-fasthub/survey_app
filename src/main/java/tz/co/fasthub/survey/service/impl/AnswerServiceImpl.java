package tz.co.fasthub.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.domain.Question;
import tz.co.fasthub.survey.repository.AnswerRepository;
import tz.co.fasthub.survey.service.AnswerService;
import tz.co.fasthub.survey.service.QuestionService;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by root on 7/17/17.
 */
@Service("answerService")
public class AnswerServiceImpl implements AnswerService {
    
    private AnswerRepository answerRepository;

    private final Answer answer = new Answer();

    private JdbcTemplate jdbcTemplate;

    private final QuestionService questionService;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionService questionService, DataSource dataSource) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public Answer getAnswerById(Long id) {
        return answerRepository.findOne(id);
     }

    @Override
    public Answer saveByQnsId(Answer answer, Question qsnId) {
        answer.setQuestion(qsnId);
        if(qsnId.getId()!=null){
            Question qs = questionService.getQsnById(qsnId.getId());
            if(qs != null){
                List<Answer> answers = getAnswerByQsnId(qs);
                if(!answers.isEmpty()){
                    for (Answer answer1 : answers) {
                        answer.setPosition(answer1.getPosition()+1);
                        break;
                    }
                }
                else {
                    answer.setPosition(1);
                }
            }
        }else{
            answer.setPosition(answer.getPosition());
        }
        return  answerRepository.save(answer);
    }



    @Override
    public List<Answer> getAnswerByQsnId(Question qsnId) {

        return answerRepository.findAllByQuestionOrderByPositionDesc(qsnId);
    }

    @Override
    public Iterable<Answer> listAllAnswers() {
        return answerRepository.findAll();
    }

    @Override
    public List<Answer> listAllAnswersByDesc() {
        return answerRepository.findAllByOrderByPositionDesc();
    }

    @Override
    public void deleteAnswer(Answer id) {
        answerRepository.delete(id);
    }



}
