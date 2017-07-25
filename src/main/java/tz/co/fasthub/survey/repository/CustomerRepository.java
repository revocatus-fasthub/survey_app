package tz.co.fasthub.survey.repository;

import org.springframework.data.repository.CrudRepository;
import tz.co.fasthub.survey.domain.Customer;

/**
 * Created by root on 7/25/17.
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>{
    Customer findOneByMsisdn(Customer customer);

}
