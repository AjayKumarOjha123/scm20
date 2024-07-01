package com.scm.scm20.helper;


import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

    public void removeMassage() {
        try {
        HttpSession session=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
        session.removeAttribute("massage");
        System.out.println("Massage removing from session");
    } catch (Exception e) {
        System.out.println("Something error in session helper"+e);
            e.printStackTrace();
        }
    }
}
