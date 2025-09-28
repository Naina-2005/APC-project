package com.scm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contact;
import com.scm.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {

    // Find contacts by user
    List<Contact> findByUser(User user);

    // Find by name (case-insensitive)
    List<Contact> findByNameContainingIgnoreCase(String name);

    // Custom query by userId
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    List<Contact> findByUserId(@Param("userId") String userId);

    // Find by user and name/email/phone containing keyword
    List<Contact> findByUserAndNameContaining(User user, String nameKeyword);
    List<Contact> findByUserAndEmailContaining(User user, String emailKeyword);
    List<Contact> findByUserAndPhoneNumberContaining(User user, String phoneKeyword);
}
