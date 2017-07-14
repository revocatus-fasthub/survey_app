package tz.co.fasthub.survey.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tz.co.fasthub.survey.domain.Contact;

import java.util.ArrayList;

/**
 * Created by root on 7/14/17.
 */
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact save(ArrayList<String> contact);
}
