package com.ecommerce.amarte.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String address;
    private String city;
    private String state;
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    

}
