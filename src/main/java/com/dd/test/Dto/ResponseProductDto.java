package com.dd.test.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProductDto {
    private long Id;
    private String Name;
    private String description;
    private double Price;
}
