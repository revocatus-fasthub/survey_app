package tz.co.fasthub.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.Contact;
import tz.co.fasthub.survey.repository.ContactRepository;
import tz.co.fasthub.survey.service.ContactService;

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
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact getContactById(Long id) {
        return contactRepository.getOne(id);
    }
}
