package tz.co.fasthub.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.Customer;
import tz.co.fasthub.survey.domain.OpenEndedAnswer;
import tz.co.fasthub.survey.domain.Question;
import tz.co.fasthub.survey.repository.OpenAnswerRepository;
import tz.co.fasthub.survey.service.OpenAnswerService;
import tz.co.fasthub.survey.service.QuestionService;

import java.util.List;

/**
 * Created by root on 7/17/17.
 */
@Service
public class OpenAnswerServiceImpl implements OpenAnswerService {

    private OpenAnswerRepository openAnswerRepository;

    private final QuestionService questionService;

    @Autowired
    public OpenAnswerServiceImpl(OpenAnswerRepository openAnswerRepository, QuestionService questionService) {
        this.openAnswerRepository = openAnswerRepository;
        this.questionService = questionService;
    }

    @Override
    public OpenEndedAnswer save(OpenEndedAnswer answer) {
        return openAnswerRepository.save(answer);
    }


    @Override
    public OpenEndedAnswer getAnswerById(Long id) {
        return openAnswerRepository.findOne(id);
     }

    @Override
    public OpenEndedAnswer getAnswerByQsnId(Question qsnId) {
        return openAnswerRepository.findByQuestion(qsnId);
    }

    @Override
    public OpenEndedAnswer saveByQnsId(OpenEndedAnswer answer, Question qsnId) {
        answer.setQuestion(qsnId);
        if(qsnId.getId()!=null) {
            return openAnswerRepository.save(answer);
        }else
        return answer;
    }

    @Override
    public OpenEndedAnswer getOneTransactionByCustomerDesc(Customer customer) {

        List<OpenEndedAnswer> questions = openAnswerRepository.findAllByCustomerOrderByIdDesc(customer);
        for (OpenEndedAnswer question : questions) {
            return question;
        }
        return null;
    }



    @Override
    public Iterable<OpenEndedAnswer> getAnswerByQsnIdAll(Long id) {

        return openAnswerRepository.findAll();
    }

    @Override
    public Iterable<OpenEndedAnswer> listAllAnswers() {
        return openAnswerRepository.findAll();
    }

    @Override
    public OpenEndedAnswer getAllByQuestion(Question question) {
        return openAnswerRepository.findByQuestion(question);
    }

/*
    @Override
    public String getAnswerByQuestion(Question question) {
        if (question!=null&&question.getAnswer()!=null) {
            StringBuilder stringBuilder =  new StringBuilder();
            stringBuilder.append(question.getQsn());
            stringBuilder.append("\n");
            for (Answer answer: question.getAnswer()){
                stringBuilder.append(answer.getPosition());
                stringBuilder.append(".");
                stringBuilder.append(answer.getAns());
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }else {
            return null;
        }
    }
*/

}
