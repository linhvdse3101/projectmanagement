package com.management.project.repositorys.user;

import com.management.project.domains.user.UserAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserAccount, Long> {

   @EntityGraph(attributePaths = "roles",  type = EntityGraph.EntityGraphType.LOAD, value = "UserAccount.roles")
   Optional<UserAccount> findByUserName(String userName);

   @Query(value = "SELECT ua.user_id as userId, " +
           "ua.user_name as userName, "+
           "ua.user_password as userPassword, "+
           "ua.email as email, "+
           "ur.role_name as roles "+
           " FROM user_account ua join user_role ur on ua.user_id = ur.user_id WHERE ua.user_name = :userName", nativeQuery = true)
   List<Object[]> findUserAndRoleAccountByUserName(@Param("userName") String userName);
}
