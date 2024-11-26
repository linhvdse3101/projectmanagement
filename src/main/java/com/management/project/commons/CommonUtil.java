package com.management.project.commons;

import com.management.project.handelexceptions.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class CommonUtil {
    public static String getCurrentUser(){
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (principal instanceof UserDetails){
                return ((UserDetails) principal).getUsername();
            }
            return principal.toString();
        }catch (Exception e){
            throw new NotFoundException(e.getMessage());
        }
    }
}
