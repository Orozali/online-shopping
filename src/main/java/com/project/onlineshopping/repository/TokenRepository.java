package com.project.onlineshopping.repository;

import com.project.onlineshopping.model.AuthenticationToken;
import com.project.onlineshopping.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken,Integer> {
    AuthenticationToken findByUserId(int id);
    @Query(value = """
      select t from AuthenticationToken t inner join UserInfo u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<AuthenticationToken> findAllValidTokenByUser(Integer id);


    Optional<AuthenticationToken> findByToken(String token);

}
