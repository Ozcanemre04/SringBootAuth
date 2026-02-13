package com.dd.test.Entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    @Column(name = "username", unique = true, length = 100)
    private String username;
    
    @Column(name="password",length = 100)
    private String password;

    private String role;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "refreshToken_id",referencedColumnName = "Id")
    private RefreshToken refreshToken;


}
