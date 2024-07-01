package com.scm.scm20.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact, String> {

    Page<Contact> findByUser(User user, Pageable pageable);
    
    Page<Contact> findByUserAndNameContaining(User user,String nameKeyword,Pageable pageable);
    
    Page<Contact> findByUserAndEmailContaining(User user,String emailKeyword,Pageable pageable);

    Page<Contact> findByUserAndPhoneNumberContaining(User user,String phoneKeyword,Pageable pageable);

}
