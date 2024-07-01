package com.scm.scm20.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.scm20.entities.User;
import com.scm.scm20.helper.Helper;
import com.scm.scm20.services.UserServices;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServices userServices;

   
    // user dashboard controller

    @RequestMapping(value = "dashboard")
    private String userDashboard() {
        return "user/dashboard";
    }

    // user profile controller

    @RequestMapping(value = "profile")
    private String userProfile(Model model ,Authentication authentication) {
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User is Logged in " + username);
        User user = userServices.getUserByEmail(username);
        System.out.println(user.getEmail());
        System.out.println(user.getName());
        model.addAttribute("logedInUser", user);
        return "user/profile";
    }
}
