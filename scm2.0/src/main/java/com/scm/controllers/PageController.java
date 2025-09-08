package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class PageController {

    @Autowired  
    private UserService userService;
    @RequestMapping( "/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        model.addAttribute("Name", "Substring Technologies");
        model.addAttribute("YouTube", "LearnCode");
        model.addAttribute("GitHub_repo","https://github.com/Rancy11" );
        return "home";
    }

    //about route
    @RequestMapping( "/about")
    public String aboutPage() {
        System.out.println("About page loading");
        return "about";
    }

    //services route
    @RequestMapping( "/services")   
    public String servicesPage() {
        System.out.println("Services page loading");
        return "services";
    }
    // contact page

    @GetMapping("/contact")
    public String contact() {
        return new String("contact");
    }

    // this is showing login page
    @GetMapping("/login")
    public String login() {
        return new String("login");
    }

    // registration page
    @GetMapping("/register")
    public String register(Model model) {

        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    //processing register
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@ModelAttribute UserForm userForm) {
        System.out.println("Processing registration");
        // fetch form data
        System.out.println(userForm);
        // UserForm
        String profilePic;
        User user = User.builder()

.name(userForm.getName())

.email(userForm.getEmail())

.password(userForm.getPassword())

.about(userForm.getAbout())

.phoneNumber(userForm.getPhoneNumber())


.build();

User savedUser = userService.saveUser(user);



// message "Registration Successful"



        System.out.println("user Saved");

        //userService.saveUser(user);
        return "redirect:/register";
        }

}