package com.dd.test.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dd.test.Entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {
} 
