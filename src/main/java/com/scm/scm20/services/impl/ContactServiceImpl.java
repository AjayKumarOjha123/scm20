package com.scm.scm20.services.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;
import com.scm.scm20.helper.ResourceNotFoundException;
import com.scm.scm20.repository.ContactRepository;
import com.scm.scm20.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Override
    public Contact save(Contact contact) {
        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {

        var oldContact = contactRepository.findById(contact.getId()).orElseThrow();

        oldContact.setName(contact.getName());
        oldContact.setEmail(contact.getEmail());
        oldContact.setPhoneNumber(contact.getPhoneNumber());
        oldContact.setAddress(contact.getAddress());
        oldContact.setFavorite(contact.isFavorite());
        oldContact.setDescription(contact.getDescription());
        oldContact.setWebsiteLink(contact.getWebsiteLink());
        oldContact.setLinkedInLink(contact.getLinkedInLink());
        oldContact.setPicture(contact.getPicture());
        oldContact.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());

        return contactRepository.save(oldContact);

    }

    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @Override
    public Contact getById(String id) {
        return contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id not found"));
    }

    @Override
    public void delete(String id) {
        var contact = contactRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id not found"));
        contactRepository.delete(contact);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByUser(user, pageable);
    }

    @Override
    public Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String direction ,User user) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    @Override
    public Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String direction ,User user) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByUserAndEmailContaining(user, emailKeyword,pageable);
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
            String direction ,User user) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepository.findByUserAndPhoneNumberContaining(user, phoneNumberKeyword, pageable);
    }
}
