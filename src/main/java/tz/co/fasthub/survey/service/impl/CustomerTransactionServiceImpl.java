package tz.co.fasthub.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.CustomerTransaction;
import tz.co.fasthub.survey.repository.CustomerTransactionRepository;
import tz.co.fasthub.survey.service.CustomerTransactionService;

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
        return null;
    }

    @Override
    public CustomerTransaction getCustomerTransactionById(Long id) {
        return null;
    }

    @Override
    public CustomerTransaction saveCustomerTransaction(CustomerTransaction customerTransaction) {
        return null;
    }

    @Override
    public void deleteCustomerTransaction(Long id) {
        customerTransactionRepository.delete(id);
    }
}
