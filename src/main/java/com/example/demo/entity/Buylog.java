package com.example.demo.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Buylog {

    @Id                        //오라클
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer amount;
    private String couponcode;
    private String productName;
    private Integer price;
    private String bank;
    private String userName;
    @Column(columnDefinition = "VARCHAR(255) DEFAULT '거래 진행중'")
    private String state;
    @Temporal(TemporalType.TIMESTAMP)
    private Date buyDate;

    @PrePersist
    protected void onCreate() {
        buyDate = new Date(); // 현재 날짜를 설정
    }
}
