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
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;

@Controller
public class PageController {

    @Autowired
    private UserService userService;

     @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        model.addAttribute("Name", "Substring Technologies");
        model.addAttribute("YouTube", "LearnCode");
        model.addAttribute("GitHub_repo", "https://github.com/Rancy11");
        return "home";
    }

    // about route
    @RequestMapping("/about")
    public String aboutPage() {
        System.out.println("About page loading");
        return "about";
    }

    // services route
    @RequestMapping("/services")
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

    // processing register
    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {
        System.out.println("Processing registration");
        // fetch form data
        //userForm
        System.out.println(userForm);
        String profilePic;

        //Validate Form data
        if(rBindingResult.hasErrors()){
            return "register";            
        }
        
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic(
                profilePic = "https://imgs.search.brave.com/hsAJm18LOTtzALcsQapPM6McZOTetrIllR4gCo6i0Dc/rs:fit:500:0:1:0/g:ce/aHR0cHM6Ly9pLnBp/bmltZy5jb20vb3Jp/Z2luYWxzLzVjLzg0/LzQyLzVjODQ0Mjhk/OTgzZDI2Y2U4NWE4/NGY5NDZlNGNlOGFi/LmpwZw");

        User savedUser = userService.saveUser(user);

        // message "Registration Successful"

        System.out.println("user Saved");


        //add the message

        Message message = Message.builder().content("Registration Successful !!").type(MessageType.green).build();

        session.setAttribute("message", message);
        // userService.saveUser(user);
        return "redirect:/register";
    }

}