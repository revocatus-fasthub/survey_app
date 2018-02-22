package tz.co.fasthub.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.Answer;
import tz.co.fasthub.survey.domain.Customer;
import tz.co.fasthub.survey.domain.CustomerTransaction;
import tz.co.fasthub.survey.domain.Question;
import tz.co.fasthub.survey.repository.CustomerTransactionRepository;
import tz.co.fasthub.survey.service.CustomerTransactionService;

import java.util.List;

/**
 * Created by root on 7/25/17.
 */

@Service
public class CustomerTransactionServiceImpl implements CustomerTransactionService {

    private  CustomerTransactionRepository customerTransactionRepository;

    @Autowired
    public CustomerTransactionServiceImpl(CustomerTransactionRepository customerTransactionRepository) {
        this.customerTransactionRepository = customerTransactionRepository;
    }


    @Override
    public Iterable<CustomerTransaction> listAllCustomerTransactionByAttendedDesc(boolean attended) {
        return customerTransactionRepository.findByAttendedOrderByIdDesc(attended);
    }

    @Override
    public CustomerTransaction getOneTransactionByCustomerDesc(Customer customer, boolean attended) {

        List<CustomerTransaction> questions = customerTransactionRepository.findAllByCustomerAndAttendedOrderByIdDesc(customer,attended);
        for (CustomerTransaction question : questions) {
            return question;
        }
        return null;
    }

    @Override
    public CustomerTransaction getCustomerTransactionById(Long id) {
        return customerTransactionRepository.findOne(id);
    }

    @Override
    public CustomerTransaction saveCustomerTransaction(CustomerTransaction customerTransaction) {
        return customerTransactionRepository.save(customerTransaction);
    }

    @Override
    public void deleteCustomerTransaction(Long id) {
        customerTransactionRepository.delete(id);
    }

    @Override
    public List<CustomerTransaction> getAllQuestionAndAnswer(Question question, Answer answer) {

        return customerTransactionRepository.findAllByQuestionAndAnswer(question, answer);
    }

    @Override
    public List<CustomerTransaction> findAllByMsisdn(String customerTransaction) {
        return customerTransactionRepository.findAllByCustomer(customerTransaction);
    }

    @Override
    public List<CustomerTransaction> findByIdGreaterThan(Long id) {
        return customerTransactionRepository.findByIdGreaterThan(id);
    }


/*

    @Override
    public List<CustomerTransaction> getAllQuestionAndAnswerDetails(Question question, CustomerTransaction answerDetails) {

        return customerTransactionRepository.findAllByQuestionAndAnswerDetails(question, answerDetails);
    }
*/

    @Override
    public List<CustomerTransaction> getAnswerByQsnId(Question question) {
        return customerTransactionRepository.findAllByQuestion(question);
    }

    @Override
    public List<CustomerTransaction> search(String query) {
        return customerTransactionRepository
                .findAllByAnswerDetailsContainingIgnoreCaseOrCustomerMsisdnContainingOrQuestionQsnContainingIgnoreCaseOrAnswerAnsContainingIgnoreCase(query,query,query,query);
    }

}
