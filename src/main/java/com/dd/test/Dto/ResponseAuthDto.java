package com.dd.test.Dto;

import lombok.Data;

@Data
public class ResponseAuthDto {
    private String AccessToken;
    private String RefreshToken;
}
