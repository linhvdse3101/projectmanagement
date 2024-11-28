package com.management.project.domains.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.management.project.domains.AbstractEntity;
import com.management.project.domains.project.Project;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserToken> tokens;

}
