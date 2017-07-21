package tz.co.fasthub.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.Question;
import tz.co.fasthub.survey.repository.QuestionRepository;
import tz.co.fasthub.survey.service.QuestionService;

import java.util.List;

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
            if(question.getQsn() != null) {
                List<Question> questions = listAllQuestionsByDesc();
                if(!questions.isEmpty()){
                    for (Question question1:questions) {
                        question.setSequence(question1.getSequence() + 1);
                        break;
                    }
                }else {
                    question.setSequence(1);
                }
            }
        return questionRepository.save(question);
    }

    @Override
    public Question getQsnById(Long id) {
        return questionRepository.findOne(id);
    }

    @Override
    public Question getQsnByAscendingId(int sequence) {
        return null;//questionRepository.findAllByOrderBySequence();
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
    public Question getQnsBySequence1(Integer id) {
        return questionRepository.findOne(Long.valueOf(id));
    }

    @Override
    public Question getQnsBySequence2(Integer id) {
        return questionRepository.findOne(Long.valueOf(id));
    }


    @Override
    public List<Question> listAllQuestionsByDesc() {

        return questionRepository.findAllByOrderBySequenceDesc();
    }


    @Override
    public List<Question> listAllQuestionsByAsc() {

        return questionRepository.findAllByOrderBySequenceAsc();
    }


}