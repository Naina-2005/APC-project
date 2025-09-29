package com.scm.services;

import com.scm.entities.Contact;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ContactServiceImplTest {

    @Autowired
    private ContactService contactService;

    @Test
    void testSaveContact() {
        Contact contact = new Contact();
        contact.setName("Test Name");
        contact.setEmail("test@example.com");
        contact.setPhoneNumber("1234567890");
        Contact saved = contactService.save(contact);
        assertNotNull(saved.getId());
        assertEquals("Test Name", saved.getName());
    }
}