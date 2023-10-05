package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Coupon {

                           //오라클
//    @GeneratedValue(strategy = GenerationType.SEQUENCE
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique=true)
    private String couponcode;
    private Integer discount;

}
