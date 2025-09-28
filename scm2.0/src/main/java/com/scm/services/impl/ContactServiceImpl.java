package com.scm.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;
import com.scm.repositories.ContactRepo;
import com.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public List<Contact> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id " + id));
    }

    @Override
    public void delete(String id) {
        var contact = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id " + id));
        contactRepo.delete(contact);
    }

    @Override
    public List<Contact> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);
    }

    @Override
    public List<Contact> getByUser(User user) {
        return contactRepo.findByUser(user);
    }

    @Override
    public List<Contact> searchByUserAndName(User user, String nameKeyword) {
        return contactRepo.findByUserAndNameContaining(user, nameKeyword);
    }

    @Override
    public List<Contact> searchByUserAndEmail(User user, String emailKeyword) {
        return contactRepo.findByUserAndEmailContaining(user, emailKeyword);
    }

    @Override
    public List<Contact> searchByUserAndPhoneNumber(User user, String phoneKeyword) {
        return contactRepo.findByUserAndPhoneNumberContaining(user, phoneKeyword);
    }
}