package com.dd.test.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
@Data
public class RequestProductDto {
    @NotEmpty(message = "Name can't  be null")
    @NotBlank(message = "Name can't  be blank")
    private String Name;

    @NotEmpty(message="description can't be null")
    @NotBlank(message = "description can't be blank")
    private String description;

    @NotNull(message="price can't be null")
    @Positive(message = "Price can't be zero or inferior to zero")
    private double Price;
}
