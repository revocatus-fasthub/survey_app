package tz.co.fasthub.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.repository.AnswerRepository;
import tz.co.fasthub.survey.service.AnswerService;
import tz.co.fasthub.survey.service.QuestionService;

/**
 * Created by root on 7/17/17.
 */
@Service("answerService")
public class AnswerServiceImpl implements AnswerService {
    
    private final AnswerRepository answerRepository;

    @Autowired
    private final QuestionService questionService;

    @Autowired
    public AnswerServiceImpl(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    @Override
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public Answer getAnswerById(Long id) {
        return answerRepository.getOne(id);
    }

    @Override
    public Answer saveByQnsId(Answer ans, Long qsnId) {
        questionService.getQsnById(qsnId);

        return  answerRepository.save(ans);
    }

    @Override
    public Iterable<Answer> listAllAnswers() {
        return answerRepository.findAll();
    }


}
