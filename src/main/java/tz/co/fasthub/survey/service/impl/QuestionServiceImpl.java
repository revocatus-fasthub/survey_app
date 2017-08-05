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
    if(question.getId()== null) {
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
    public Question getNextQuestion(Question currentCurrent){

        List<Question> questions = listAllQuestionsByAsc();
        if(!questions.isEmpty()){
            boolean detector=false;
            for (Question question1:questions) {
                if (detector){
                    return question1;
                }
                if (question1.equals(currentCurrent)){
                    detector=true;
                }

            }
        }else {
            return null;
        }
        return null;
    }

    @Override
    public Question getQsnById(Long id) {
        return questionRepository.findOne(id);
    }

    @Override
    public Question getQnOneBySequence() {

        List<Question> questions = questionRepository.findAllByOrderBySequenceAsc();
        for (Question question : questions) {
            return question;
        }
        return null;
    }


    @Override
    public List<Question> listAllQuestionsByDesc() {

        return questionRepository.findAllByOrderBySequenceDesc();
    }


    @Override
    public List<Question> listAllQuestionsByAsc() {

        return questionRepository.findAllByOrderBySequenceAsc();
    }

    @Override
    public Question update(Question question) {
        question.setSequence(question.getSequence());
        return questionRepository.save(question);
    }

    @Override
    public void deleteQuestion(Long id) {
        questionRepository.delete(id);
    }



}