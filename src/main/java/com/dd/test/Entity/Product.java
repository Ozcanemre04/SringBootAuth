package com.dd.test.Entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    @Column(nullable = false,length = 100)
    @NotBlank()
    private String Name;

    @Column(nullable = false,length = 250)
    @NotBlank()
    private String description;

    @Column(nullable = false)
    private double Price;

    public long getId() {
        return Id;
    }
    public String getName() {
        return Name;
    }
    public String getDescription() {
        return description;
    }
    public double getPrice() {
        return Price;
    }
    public void setId(long id) {
        Id = id;
    }
    public void setName(String name) {
        Name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(double price) {
        Price = price;
    }
}
