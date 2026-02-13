package com.dd.test.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dd.test.Dto.RequestAuthDto;
import com.dd.test.Dto.RequestRefreshTokenDto;
import com.dd.test.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    
    private final AuthService authService;
    
    @PostMapping("/register")
    public ResponseEntity<?> Register(@Valid @RequestBody RequestAuthDto requestAuthDto) {
        try {
            return ResponseEntity.ok(authService.Register(requestAuthDto));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> Login(@Valid @RequestBody RequestAuthDto requestAuthDto) {
        try {
            return ResponseEntity.ok(authService.Login(requestAuthDto));
            
        } catch (Exception e) {
            if(e.getMessage().contains("wrong")){

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            }
            return ResponseEntity.badRequest().body(e.getMessage());
            
      
        }

    }
    
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RequestRefreshTokenDto refreshTokenDto){
        try {
            return ResponseEntity.ok(authService.refreshToken(refreshTokenDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    
}
