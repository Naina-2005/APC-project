package com.scm.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {
        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername(); // usually email
        } else {
            return principal.toString();
        }
    }
}
