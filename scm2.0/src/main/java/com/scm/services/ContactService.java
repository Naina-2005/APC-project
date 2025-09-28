package com.scm.services;

import java.util.List;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {
    // save contact
    Contact save(Contact contact);

    // update contact
    Contact update(Contact contact);

    // get all contacts
    List<Contact> getAll();

    // get contact by id
    Contact getById(String id);

    // delete contact
    void delete(String id);

    // get contacts by userId
    List<Contact> getByUserId(String userId);

    // get contacts by user
    List<Contact> getByUser(User user);

    // search contacts by user and name/email/phone
    List<Contact> searchByUserAndName(User user, String nameKeyword);
    List<Contact> searchByUserAndEmail(User user, String emailKeyword);
    List<Contact> searchByUserAndPhoneNumber(User user, String phoneKeyword);
}