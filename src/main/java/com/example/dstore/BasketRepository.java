package com.example.dstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BasketRepository extends JpaRepository<Basket, Long> {

}
