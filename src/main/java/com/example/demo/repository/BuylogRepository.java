package com.example.demo.repository;

import com.example.demo.entity.Buylog;
import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuylogRepository extends JpaRepository<Buylog, Integer> {
}
