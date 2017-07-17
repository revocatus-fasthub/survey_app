package tz.co.fasthub.survey.repository;

import org.springframework.data.repository.CrudRepository;
import tz.co.fasthub.survey.domain.Contact;

import java.util.ArrayList;

/**
 * Created by root on 7/14/17.
 */
public interface ContactRepository extends CrudRepository<Contact, Long> {
    Contact save(ArrayList<String> contact);
}
