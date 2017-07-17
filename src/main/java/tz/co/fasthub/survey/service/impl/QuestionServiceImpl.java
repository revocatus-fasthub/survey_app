package tz.co.fasthub.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.Question;
import tz.co.fasthub.survey.repository.QuestionRepository;
import tz.co.fasthub.survey.service.QuestionService;

/**
 * Created by root on 7/17/17.
 */

@Service("questionService")
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public Question getQsnById(Long id) {
        return questionRepository.getOne(id);
    }

    @Override
    public Question getQsnByAscendingId(int sequence) {
        return questionRepository.findAllByOrderByIdAsc();
    }

    @Override
    public Question getQsnByDescendingId(int sequence) {
        return questionRepository.findAllByOrderByIdDesc(sequence);
    }

    @Override
    public Question getQnsBySequence(Integer id) {
        return questionRepository.findAllByOrderByIdDesc(id);
    }

    @Override
    public Iterable<Question> listAllTalent() {
        return questionRepository.findAll();
    }


}