package com.management.project.services.auth.serviceImpls;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.project.auth.JwtUtil;
import com.management.project.auth.SecurityUtils;
import com.management.project.domains.user.UserAccount;
import com.management.project.domains.user.UserRole;
import com.management.project.domains.user.UserToken;
import com.management.project.enums.Role;
import com.management.project.enums.TokenType;
import com.management.project.handelexceptions.ValidateException;
import com.management.project.repositorys.user.UserRepository;
import com.management.project.repositorys.user.UserRoleRepository;
import com.management.project.repositorys.user.UserTokenRepository;
import com.management.project.requests.RegisterRequest;
import com.management.project.responses.AuthResponse;
import com.management.project.services.auth.UserAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserAccountService {

    private final UserRepository userRepository;
    private final UserRoleRepository roleRepository;
    private final UserTokenRepository userTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthResponse createAccountRequest(RegisterRequest request) {
        log.info("createAccountRequest: " + UserDetailsServiceImpl.class);
        if (Objects.isNull(request) || !StringUtils.hasText(request.getUserName()) || !StringUtils.hasText(request.getUserPassword())) {
            log.error("Username or password is not empty");
        }
        String currentRole = SecurityUtils.getLoggedInUserRole();
        UserAccount userAccount = userRepository.findByUserName(request.getUserName()).orElse(null);
        if (Objects.nonNull(userAccount)) {
            log.error("User Name is exited");
            throw new ValidateException("User Name is exited");
        }

//        if (currentRole.equals(Role.ADMIN.getName())) {
        UserAccount acc = UserAccount.builder()
                .userName(request.getUserName())
                .userPassword(passwordEncoder.encode(request.getUserPassword()))
                .roles(new HashSet<>())
                .email(request.getEmail())
                .build();
        UserRole currRole = UserRole.builder()
                .roleName(Role.ADMIN.getName())
                .user(acc)  // Gán user vào vai trò
                .build();
//        acc.getRoles().add(currRole);  // Thêm vai trò vào UserAccount
        acc.prePersist(); // Đặt các giá trị prePersist cho UserAccount
        userRepository.save(acc); // Lưu UserAccount trước
        // Thiết lập mối quan hệ giữa UserAccount và UserRole
//        currRole.prePersist(); // Đặt các giá trị prePersist cho UserRole
        roleRepository.save(currRole); // Sau đó lưu UserRole
        log.debug("User Account ID: " + acc.getUserId());
        log.debug("User Role ID: " + currRole.getRoleId());
        String accessToken = jwtUtil.generateToken(acc);
        String reFreshToken = jwtUtil.refreshToken(acc);
        return AuthResponse.builder().accessToken(accessToken).refreshToken(reFreshToken).tokenType(TokenType.BEARER.name()).build();
//        } else {
//            log.error("Only admin can create user");
//            throw new ValidateException("Only admin can create user");
//        }
    }

    @Override
    public AuthResponse authUserDetailsService(String userName, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userName,
                        password
                )
        );
        UserAccount acc = userRepository.findByUserName(userName).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        if (!acc.getRoles().isEmpty()) {
            log.info("Roles size: " + acc.getRoles().size());
            for (UserRole role : acc.getRoles()) {
                log.info("Role: " + role.getRoleName());
            }
        }
        String accessToken = jwtUtil.generateToken(acc);
        String refreshToken = jwtUtil.refreshToken(acc);
        revokeAllUserTokens(acc);
        saveToken(acc, accessToken);
        return AuthResponse.builder().tokenType(TokenType.BEARER.name()).accessToken(accessToken).refreshToken(refreshToken).build();
    }


    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        log.info("refreshToken: " + UserDetailsServiceImpl.class);
        final String refreshToken;
        final String userName;
        try {
            final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
                return;
            }
            refreshToken = authHeader.substring(7);
            userName = jwtUtil.extractUsername(refreshToken);
            if (StringUtils.hasText(userName)) {
                var user = userRepository.findByUserName(userName)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

                if (jwtUtil.validateToken(refreshToken, user)) {
                    var accessToken = jwtUtil.generateToken(user);

                    revokeAllUserTokens(user);
                    saveUserToken(user, accessToken);
                    var authResponse = AuthResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .tokenType("Bearer")
                            .build();
                    new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
                }
            }
        } catch (Exception e) {
            StackTraceElement strack = Thread.currentThread().getStackTrace()[2];
            log.error("refreshToken: " + UserDetailsServiceImpl.class + "line: " + strack.getLineNumber());
        }
    }

    // out interface function start
    private void revokeAllUserTokens(UserAccount user) {
        var validUserTokens = userTokenRepository.findAllValidTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        userTokenRepository.saveAll(validUserTokens);
    }

    private void saveUserToken(UserAccount user, String token) {
        if (Objects.nonNull(user)) {
            UserToken newToken = UserToken.builder()
                    .user(user)
                    .token(token)
                    .tokenType(TokenType.BEARER)
                    .revoked(false)
                    .expired(false)
                    .build();
            userTokenRepository.save(newToken);
        }
    }

    private void saveToken(UserAccount user, String token) {
        UserToken userToken = UserToken.builder().user(user).token(token).tokenType(TokenType.BEARER).expired(false).revoked(false).build();
        userToken.prePersist();
        userTokenRepository.save(userToken);
    }

}