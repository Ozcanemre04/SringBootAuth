package com.dd.test.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.dd.test.Entity.User;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    
}
