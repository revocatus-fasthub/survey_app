package tz.co.fasthub.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.Customer;
import tz.co.fasthub.survey.domain.CustomerTransaction;
import tz.co.fasthub.survey.repository.CustomerTransactionRepository;
import tz.co.fasthub.survey.service.CustomerTransactionService;

import java.util.List;

/**
 * Created by root on 7/25/17.
 */

@Service
public class CustomerTransactionServiceImpl implements CustomerTransactionService {

    private final CustomerTransactionRepository customerTransactionRepository;

    @Autowired
    public CustomerTransactionServiceImpl(CustomerTransactionRepository customerTransactionRepository) {
        this.customerTransactionRepository = customerTransactionRepository;
    }


    @Override
    public Iterable<CustomerTransaction> listAllCustomerTransaction() {
        return customerTransactionRepository.findAllByOrderByIdDesc();
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
    public Iterable<CustomerTransaction> lisAllByCustomerAndAttended(Customer customer, boolean attended) {
        return customerTransactionRepository.findAllByCustomerAndAttendedOrderByIdDesc(customer,attended);
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
}
