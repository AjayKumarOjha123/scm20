package com.scm.scm20.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm.scm20.entities.User;
import com.scm.scm20.helper.Helper;
import com.scm.scm20.services.UserServices;

@ControllerAdvice
public class RootController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserServices userServices;

    @ModelAttribute
    public void addLogedInUserInformation(Model model, Authentication authentication) {
        if (authentication == null) {
            return;
        }
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User is Logged in " + username);
        User user = userServices.getUserByEmail(username);
        System.out.println(user.getEmail());
        System.out.println(user.getName());
        model.addAttribute("logedInUser", user);

    }

}
