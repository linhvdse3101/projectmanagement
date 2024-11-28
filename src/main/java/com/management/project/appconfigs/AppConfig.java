package com.management.project.appconfigs;

import com.management.project.commons.database.CommonMapper;
import com.management.project.repositorys.user.UserRepository;
import com.management.project.responses.commons.UserAccountDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final UserRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            // Query the repository for user and role information
            List<Object[]> userAndRoles = repository.findUserAndRoleAccountByUserName(username);

            if (userAndRoles.isEmpty()) {
                log.error("User not found: " + username);
                throw new UsernameNotFoundException("User not found: " + username);
            }
            CommonMapper<UserAccountDto> commonMapper = new CommonMapper<>(UserAccountDto::new);
            List<UserAccountDto> userAccountDtos = commonMapper.mapToObjects(userAndRoles);
            // Convert UserAccountDto to UserDetails
            return userAccountDtos.isEmpty() ? null :  userAccountDtos.get(0);
        };
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}