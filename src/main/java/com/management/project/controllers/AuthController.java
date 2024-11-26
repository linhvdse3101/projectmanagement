package com.management.project.controllers;

import com.management.project.requests.AuthRequest;
import com.management.project.requests.RegisterRequest;
import com.management.project.responses.AuthResponse;
import com.management.project.responses.CommonResponse;
import com.management.project.services.auth.UserAccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Auth", description = "Auth management APIs")
@RequiredArgsConstructor
public class AuthController {

    private final UserAccountService service;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse<AuthResponse>> register(@RequestBody @Valid RegisterRequest request) {
        var response = service.createAccountRequest(request);
        return ResponseEntity.ok(new CommonResponse<>(response));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<CommonResponse<AuthResponse>> authenticate(@RequestBody AuthRequest request) {
        var response = service.authUserDetailsService(request.getUserName(), request.getPassWord());
        return ResponseEntity.ok(new CommonResponse<>(response));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        service.refreshToken(request, response);
    }
}
