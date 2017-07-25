package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.CustomerTransaction;

/**
 * Created by root on 7/25/17.
 */
public interface CustomerTransactionService {

    Iterable<CustomerTransaction> listAllCustomerTransaction();

    CustomerTransaction getCustomerTransactionById(Long id);

    CustomerTransaction saveCustomerTransaction(CustomerTransaction customerTransaction);

    void deleteCustomerTransaction(Long id);
}
