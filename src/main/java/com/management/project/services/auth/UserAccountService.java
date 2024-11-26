package com.management.project.services.auth;

import com.management.project.requests.RegisterRequest;
import com.management.project.responses.AuthResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public interface UserAccountService {
    AuthResponse createAccountRequest(RegisterRequest request);
    AuthResponse authUserDetailsService(String userName, String password);
    void refreshToken(HttpServletRequest request, HttpServletResponse response);
}
