package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Contact;

/**
 * Created by root on 7/14/17.
 */
public interface ContactService {

    Contact save(Contact contact);
    Contact getContactById(Long id);
}
