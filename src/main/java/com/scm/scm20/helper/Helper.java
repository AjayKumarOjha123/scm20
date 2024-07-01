package com.scm.scm20.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {
        // dekhna user kis provider se login kiya hai ex-google,github or manually

        if (authentication instanceof OAuth2AuthenticationToken) {
            // we can say user is logged in using social provider ex-google,github
            var outh2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = outh2AuthenticationToken.getAuthorizedClientRegistrationId();

            var oauth2User = (OAuth2User) authentication.getPrincipal();
            String username = "";

            if (clientId.equalsIgnoreCase("google")) {
                // sign -in with google
                System.out.println("Getting Email from google");
                username = oauth2User.getAttribute("email").toString();
            }

            else if (clientId.equalsIgnoreCase("github")) {
                // sign -in with github
                System.out.println("Getting email from github");
                username = oauth2User.getAttribute("email") != null ? oauth2User.getAttribute("name")
                        : oauth2User.getAttribute("login").toString() + "@gmail.com";
            }

            return username;

        } else {
            return authentication.getName();
        }
        
    }
}
