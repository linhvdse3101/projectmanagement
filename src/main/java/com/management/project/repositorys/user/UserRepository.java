package com.management.project.repositorys.user;

import com.management.project.domains.user.UserAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Long> {

   @EntityGraph(attributePaths = "roles",  type = EntityGraph.EntityGraphType.LOAD, value = "UserAccount.roles")
   Optional<UserAccount> findByUserName(String userName);

}
