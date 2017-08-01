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
    public OpenEndedAnswer getAnswerByQsnId(Question qsnId) {
        return openAnswerRepository.findByQuestion(qsnId);
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
    public OpenEndedAnswer getAllByQuestion(Question question) {
        return openAnswerRepository.findByQuestion(question);
    }

}
