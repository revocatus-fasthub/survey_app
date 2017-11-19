package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Customer;

/**
 * Created by naaminicharles on 7/25/17.
 */
public interface CustomerService {


    Iterable<Customer> listAllCustomers();

    Customer getCustomerById(Long id);

    Customer getCustomerByMsisdn(String msisdn);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(Long id);
}
