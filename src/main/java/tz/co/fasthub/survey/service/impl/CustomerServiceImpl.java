package tz.co.fasthub.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.Customer;
import tz.co.fasthub.survey.repository.CustomerRepository;
import tz.co.fasthub.survey.service.CustomerService;

/**
 * Created by root on 7/25/17.
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Iterable<Customer> listAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findOne(id);
    }

    @Override
    public Customer getCustomerByMsisdn(Customer customer) {
        return customerRepository.findOneByMsisdn(customer);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long id) {
        customerRepository.delete(id);

    }
}
