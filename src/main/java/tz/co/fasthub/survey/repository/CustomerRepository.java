package tz.co.fasthub.survey.repository;

import org.springframework.data.repository.CrudRepository;
import tz.co.fasthub.survey.domain.Customer;

/**
 * Created by naaminicharles on 7/25/17.
 */
public interface CustomerRepository extends CrudRepository<Customer, Long>{
    Customer findCustomerByMsisdn(String msisdn);
}
