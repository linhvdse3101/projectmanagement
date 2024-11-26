package com.management.project.repositorys.user;

import com.management.project.domains.user.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserTokenRepository extends JpaRepository<UserToken,Long> {
    @Query(value = """
            select t from UserToken t inner join UserAccount u 
            on t.user.userId = u.userId
            where u.userId = :id and (t.expired = false and t.revoked = false )
            """)
    List<UserToken> findAllValidTokenByUser(Long id);

    Optional<UserToken> findByToken(String token);
}
