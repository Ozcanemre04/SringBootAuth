package com.dd.test.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dd.test.Entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
   Optional<RefreshToken> findByToken(String token);
   
}
