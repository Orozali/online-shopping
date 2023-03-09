package com.project.onlineshopping.repository;

import com.project.onlineshopping.model.AuthenticationToken;
import com.project.onlineshopping.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken,Integer> {
    AuthenticationToken findByUserId(int id);
    AuthenticationToken findByUser(UserInfo user);
    @Query("select p from AuthenticationToken p where p.token=?1")
    Optional<AuthenticationToken> findByToken(String token);
}
