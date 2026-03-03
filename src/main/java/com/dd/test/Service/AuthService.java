package com.dd.test.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dd.test.Configuration.JwtUtils;
import com.dd.test.Dto.RequestAuthDto;
import com.dd.test.Dto.RequestRefreshTokenDto;
import com.dd.test.Dto.ResponseAuthDto;
import com.dd.test.Entity.RefreshToken;
import com.dd.test.Entity.User;
import com.dd.test.Repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public String Register(RequestAuthDto requestAuthDto){
      User user = new User();
      user.setUsername(requestAuthDto.getUsername());
      user.setPassword(requestAuthDto.getPassword());
      var userExist= userRepository.findByUsername(user.getUsername());

      if(userExist !=null){
        throw new RuntimeException("this username Already exists");
      }
      user.setRole("ROLE_USER");
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      userRepository.save(user);
      return "User registered";
    }

    public ResponseAuthDto Login(RequestAuthDto requestAuthDto){
         User user = new User();
         user.setUsername(requestAuthDto.getUsername());
         user.setPassword(requestAuthDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if(authentication.isAuthenticated()){
          ResponseAuthDto dto = new ResponseAuthDto();
          RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getUsername());
          dto.setAccessToken(jwtUtils.GenerateToken(user.getUsername()));
          dto.setRefreshToken(refreshToken.getToken());
          return dto;
        }
        else{
          throw new RuntimeException("wrong username or password ");

        }
    }
    
    public ResponseAuthDto refreshToken(RequestRefreshTokenDto requestRefreshTokenDto){
      RefreshToken verifyToken = refreshTokenService.verifyToken(requestRefreshTokenDto.getToken());
      String getUsername = verifyToken.getUser().getUsername();
      RefreshToken CreateRefreshtoken = refreshTokenService.createRefreshToken(getUsername);
      ResponseAuthDto dto  = new ResponseAuthDto();
      dto.setAccessToken(jwtUtils.GenerateToken(getUsername));
      dto.setRefreshToken(CreateRefreshtoken.getToken());
      return dto;
    }
}
