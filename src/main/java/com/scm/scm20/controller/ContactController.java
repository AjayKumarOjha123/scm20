package com.scm.scm20.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.scm20.entities.Contact;
import com.scm.scm20.entities.User;
import com.scm.scm20.forms.ContactForm;
import com.scm.scm20.forms.ContactSearchForm;
import com.scm.scm20.helper.AppConstants;
import com.scm.scm20.helper.Helper;
import com.scm.scm20.helper.Massage;
import com.scm.scm20.helper.MassageType;
import com.scm.scm20.services.ContactService;
import com.scm.scm20.services.ImageService;
import com.scm.scm20.services.UserServices;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/user/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserServices userServices;

    @Autowired
    private ImageService imageService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Page<Contact> searchByName;

    private Contact user;

    @RequestMapping(value = "/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "/user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,
            Authentication authentication, HttpSession session) {
        System.out.println(contactForm);

        if (result.hasErrors()) {
            session.setAttribute("massage", Massage
                    .builder()
                    .contant("Please Correct The Following Errors")
                    .type(MassageType.red)
                    .build());
            return "/user/add_contact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userServices.getUserByEmail(username);

        // process the form data

        // image process

        String filename = UUID.randomUUID().toString();
        String fileUrl = imageService.uploadImage(contactForm.getContactImage(), filename);

        Contact contact = new Contact();

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setFavorite(contactForm.isFavorate());
        contact.setUser(user);
        contact.setCloudinaryImagePublicId(filename);
        contact.setPicture(fileUrl);

        // save in database
        contactService.save(contact);
        // show added massage
        session.setAttribute("massage", Massage
                .builder()
                .contant("You Have Successfuly Added New Contact!")
                .type(MassageType.green)
                .build());

        return "redirect:/user/contacts/add";
    }

    @RequestMapping
    public String viewAllContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
            Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServices.getUserByEmail(username);
        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "/user/contacts";
    }

    // search handler

    @RequestMapping("/search")
    public String searchHandler(
            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
            Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServices.getUserByEmail(username);
        Page<Contact> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phoneNumber")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,
                    direction, user);
        }

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("contactSearchForm", contactSearchForm);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/search";
    }

    // delete Contact
    @RequestMapping("delete/{id}")
    public String deleteContact(@PathVariable String id) {
        contactService.delete(id);
        return "redirect:/user/contacts";
    }

    // update cantact form view

    @GetMapping("/view/{id}")
    public String updateContactFormView(@PathVariable String id, Model model) {

        var contact = contactService.getById(id);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorate(contact.isFavorite());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("contactForm", contactForm);
        model.addAttribute("id", id);

        return "user/update_contacts_view";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public String updateContact(@PathVariable String id, @Valid @ModelAttribute ContactForm contactForm,
            BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "user/update_contacts_view";
        }

        var contact = contactService.getById(id);
        contact.setId(id);
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorate());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        // process image

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            String fileName = UUID.randomUUID().toString();
            String imgageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
            contact.setCloudinaryImagePublicId(fileName);
            contact.setPicture(imgageUrl);
            contactForm.setPicture(imgageUrl);
        }

        Contact updateContact = contactService.update(contact);
        System.out.println(updateContact);

        return "redirect:/user/contacts";
    }

}
