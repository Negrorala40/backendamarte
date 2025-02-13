package com.ecommerce.amarte.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Address")
public class Addres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String adress;
    private String city;
    private String state;
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    

}
