package com.management.project.auth;

import com.management.project.responses.commons.UserAccountDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class SecurityUtils {
    // Static method to get the logged-in user
    public static String getLoggedInUsername() {
        // Get the Authentication object from the SecurityContext
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if authentication is not null and the principal is an instance of UserDetails
        if (authentication != null && authentication.getPrincipal() instanceof UserAccountDto) {
            UserAccountDto userDetails = (UserAccountDto) authentication.getPrincipal();
            return userDetails.getUsername();
        }

        // Return null or throw an exception if no user is authenticated
        return null;
    }

    // Optionally, get the full UserDetails object
    public static UserAccountDto getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserAccountDto) {
            return (UserAccountDto) authentication.getPrincipal();
        }
        return null;
    }

    public static String getLoggedInUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            if (authorities != null && !authorities.isEmpty()) {
                // Assuming user has only one role, you can return the first role (or modify if necessary)
                return authorities.iterator().next().getAuthority();
            }
        }
        return null;  // Return null if no role is found
    }
}