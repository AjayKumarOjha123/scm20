package com.scm.scm20.controller;

import com.scm.scm20.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.scm20.forms.UserForm;
import com.scm.scm20.helper.Massage;
import com.scm.scm20.helper.MassageType;
import com.scm.scm20.services.UserServices;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

    @Autowired
    private UserServices userServices;
    private User saveUser;


    //index Page Controller

    @RequestMapping("/")
    public String indexController() {
        return "redirect:/home";
    }

    // Home Page Controller

    @RequestMapping("/home")
    public String homeController() {
        return "home";
    }

    // About Page Controller

    @RequestMapping("/about")
    public String aboutController() {
        return "about";
    }

    // Service Page Controller

    @RequestMapping("/services")
    public String servicesController() {
        return "services";
    }

    // Contact Page Controller

    @RequestMapping("/contact")
    public String contactController() {
        return "contact";
    }

    // Login Page Controller

    @RequestMapping("/login")
    public String loginController() {
        return "login";
    }

    // Register Page Controller

    @RequestMapping("/register")
    public String registerController(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    // Do-Register Controller

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String doRegister(@ModelAttribute UserForm userForm, HttpSession session) {
        System.out.println("Processing Registation");
        System.out.println(userForm);
        // User user = User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .build();

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAbout(userForm.getAbout());
        user.setProfilePic(
                "https://p7.hiclipart.com/preview/722/101/213/computer-icons-user-profile-circle-abstract.jpg");

        User saveUser = userServices.saveUser(user);
        System.out.println("User saved ");

        Massage massage = Massage.builder().contant("Registation Got Successful! Login Now").type(MassageType.red).build();
        session.setAttribute("massage", massage);
        return "redirect:/register";
    }

}
