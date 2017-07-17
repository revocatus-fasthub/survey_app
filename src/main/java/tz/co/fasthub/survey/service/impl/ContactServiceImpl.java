package tz.co.fasthub.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.Contact;
import tz.co.fasthub.survey.repository.ContactRepository;
import tz.co.fasthub.survey.service.ContactService;

import java.util.ArrayList;

/**
 * Created by root on 7/14/17.
 */
@Service("contactService")
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    @Override
    public Iterable<Contact> save(ArrayList<Contact> contact) {
        return contactRepository.save(contact);
    }

}
