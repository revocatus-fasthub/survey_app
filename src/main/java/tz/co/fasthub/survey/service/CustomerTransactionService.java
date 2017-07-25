package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Customer;
import tz.co.fasthub.survey.domain.CustomerTransaction;

/**
 * Created by root on 7/25/17.
 */
public interface CustomerTransactionService {

    Iterable<CustomerTransaction> listAllCustomerTransactionByAttendedDesc(boolean attended);

    CustomerTransaction getOneTransactionByCustomerDesc(Customer customer, boolean attended);

    Iterable<CustomerTransaction> lisAllByCustomerAndAttended(Customer customer, boolean attended);

    CustomerTransaction getCustomerTransactionById(Long id);

    CustomerTransaction saveCustomerTransaction(CustomerTransaction customerTransaction);

    void deleteCustomerTransaction(Long id);
}
