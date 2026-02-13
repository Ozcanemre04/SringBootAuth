package com.dd.test.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RequestAuthDto {
    @NotEmpty(message="username can't be null")
    @NotBlank(message = "username can't be blank")
    private String username;
    
    @NotEmpty(message="password can't be null")
    @NotBlank(message = "password can't be blank")
    private String password;
}
