package com.scm.scm20.services;

import java.util.List;

import org.springframework.data.domain.Page;
import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;

public interface ContactService {

    Contact save(Contact contact);

    Contact update(Contact contact);

    List<Contact> getAll();

    Contact getById(String id);

    void delete(String id);

    Page<Contact> searchByName(String nameKeyword,int size,int page,String sortBy,String direction,User user);
    
    Page<Contact> searchByEmail(String emailKeyword,int size,int page,String sortBy,String direction,User user);
    
    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword,int size,int page,String sortBy,String direction,User user);


    Page<Contact> getByUser(User User, int page, int size, String sortField, String sortDirection);
}
