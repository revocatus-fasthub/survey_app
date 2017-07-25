package tz.co.fasthub.survey.repository;

import org.springframework.data.repository.CrudRepository;
import tz.co.fasthub.survey.domain.Customer;
import tz.co.fasthub.survey.domain.CustomerTransaction;

import java.util.List;

/**
 * Created by root on 7/25/17.
 */
public interface CustomerTransactionRepository extends CrudRepository<CustomerTransaction, Long>{
    List<Customer> findAllByCustomerId(Customer customer);
}
