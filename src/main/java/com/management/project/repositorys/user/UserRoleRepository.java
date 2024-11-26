package com.management.project.repositorys.user;

import com.management.project.domains.user.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findUserRolesByRoleName(String roleName);
}
