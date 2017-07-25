package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Customer;

/**
 * Created by root on 7/25/17.
 */
public interface CustomerService {


    Iterable<Customer> listAllCustomers();

    Customer getCustomerById(Long id);

    Customer saveCustomer(Customer customer);

    void deleteCustomer(Long id);
}
