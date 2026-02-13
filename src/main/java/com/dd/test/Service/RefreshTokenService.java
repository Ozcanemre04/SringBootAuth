package com.dd.test.Service;

import java.time.Instant;
import java.util.UUID;


import org.springframework.stereotype.Service;

import com.dd.test.Entity.RefreshToken;
import com.dd.test.Entity.User;
import com.dd.test.Repository.RefreshTokenRepository;
import com.dd.test.Repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Service
@RequiredArgsConstructor
@Slf4j
public class RefreshTokenService {
    
    private final RefreshTokenRepository refreshTokenRepository;
   
    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(String username){
        User user = userRepository.findByUsername(username);
        var findtoken = refreshTokenRepository.findById(user.getRefreshToken().getId())
                                               .orElseThrow(()-> new RuntimeException("not found"));
        RefreshToken refreshToken=RefreshToken.builder()
                    .token(UUID.randomUUID().toString())
                    .expiryDate(Instant.now().plusMillis(6000000))
                    .build();
        if(findtoken!=null){
            user.setRefreshToken(null);
            userRepository.save(user);
            refreshTokenRepository.deleteById(findtoken.getId());
            saveToken(user, refreshToken);
            return refreshToken;
        }
        saveToken(user, refreshToken);
        return refreshToken;
        
    }

    private void saveToken(User user,RefreshToken refreshToken){
         user.setRefreshToken(refreshToken);
        refreshToken.setUser(user);
        userRepository.save(user);
    }
   
  
    public RefreshToken verifyToken(String token){
    RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(()-> new RuntimeException("token not found"));
       if(refreshToken.getExpiryDate().compareTo(Instant.now())<0){
          throw new RuntimeException("token expired");
       }
       return refreshToken;
       
    }
}
