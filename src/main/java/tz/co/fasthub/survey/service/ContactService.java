package tz.co.fasthub.survey.service;

import tz.co.fasthub.survey.domain.Contact;

import java.util.ArrayList;

/**
 * Created by root on 7/14/17.
 */
public interface ContactService {

    Iterable<Contact> save(ArrayList<Contact> contact);

}
