package com.example.dstore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Basket {

    private Long id;
    private String goodname;
    private int price;

    private String username;

    protected Basket(){};


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public  void setId(Long id){this.id = id;}

    public String getGoodname() {
        return goodname;
    }
    public  void setGoodname(String goodname){this.goodname = goodname;}

    public int getPrice() {
        return price;
    }
    public  void setPrice(int price){this.price = price;}

    public String getUsername() {
        return username;
    }
    public  void setUsername(String username){this.username = username;}
}
