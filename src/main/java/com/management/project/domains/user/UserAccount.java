package com.management.project.domains.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.management.project.domains.AbstractEntity;
import com.management.project.domains.project.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(name = "UserAccount.roles", attributeNodes = @NamedAttributeNode("roles"))
@NamedEntityGraph(name = "UserAccount.projects", attributeNodes = @NamedAttributeNode("projects"))
public class UserAccount extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @NotBlank
    @Min(value = 6)
    private String userName;
    @NotBlank
    @Min(value = 6)
    private String userPassword;
    @Email(message = "email not validate")
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<UserRole> roles = new HashSet<>(); // Change from UserRole to Set<UserRole>

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Project> projects = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert UserRoles to GrantedAuthorities
        return getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());
    }

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JsonManagedReference
    private Set<UserToken> tokens;

}
