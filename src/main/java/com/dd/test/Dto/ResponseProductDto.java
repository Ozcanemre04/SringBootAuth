package com.dd.test.Dto;
import lombok.Data;

@Data
public class ResponseProductDto {
    private long Id;
    private String Name;
    private String description;
    private double Price;
}
