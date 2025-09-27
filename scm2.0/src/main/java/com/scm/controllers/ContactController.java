package com.scm.controllers;

import java.util.*;


import org.slf4j.Logger;
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

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;

import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactService;

import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

   @Autowired
private ContactService contactService;


      @Autowired
    private UserService userService;


    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        // Add an empty contact object for the form binding
        model.addAttribute("contactForm",contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult result,Authentication authentication,HttpSession session)  {

       
        if (result.hasErrors()) {

            // result.getAllErrors().forEach(error -> logger.info(error.toString()));

            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());
            return "user/add_contact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        // form ---> contact

        User user = userService.getUserByEmail(username);
        
        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());

        contactService.save(contact);   // instead of ContactService.save(contact)

        
        System.out.println(contactForm);

         session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .type(MessageType.green)
                        .build());

        return "redirect:/user/contacts/add";

       
    }

    @RequestMapping
    public String viewContacts(Model model,Authentication authentication){
        //     @RequestParam(value = "page", defaultValue = "0") int page,
        //     @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
        //     @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
        //     @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
        //     

        // // load all the user contacts
        String username = Helper.getEmailOfLoggedInUser(authentication);

         User user = userService.getUserByEmail(username);

         List<Contact> contacts = contactService.getByUser(user);

         model.addAttribute("contacts", contacts);
        // model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        // model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/contacts";
    }

    //  @RequestMapping("/search")
    // public String searchHandler(
    //     @RequestParam("field") String field,
    //     @RequestParam("keyword") String value    ){

    //         // @ModelAttribute ContactSearchForm contactSearchForm,
    //         // @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
    //         // @RequestParam(value = "page", defaultValue = "0") int page,
    //         // @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
    //         // @RequestParam(value = "direction", defaultValue = "asc") String direction,
    //         // Model model,
    //         // Authentication authentication) {

    //      logger.info("field {} keyword {}", field,value);

    //     // var user = userService.getUserByEmail(Helper.getEmailOfLoggedInUser(authentication));

    //     // Page<Contact> pageContact = null;
    //     // if (contactSearchForm.getField().equalsIgnoreCase("name")) {
    //     //     pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
    //     //             user);
    //     // } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
    //     //     pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
    //     //             user);
    //     // } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
    //     //     pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,
    //     //             direction, user);
    //     // }

    //     // logger.info("pageContact {}", pageContact);

    //     // model.addAttribute("contactSearchForm", contactSearchForm);

    //     // model.addAttribute("pageContact", pageContact);

    //     // model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

    //     return "user/search";
    // }
    

    

}
