package com.example.dstore;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BasketRepository extends JpaRepository<Basket, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Basket e WHERE e.username = :value")
    void deletebyuser(String value);
}
