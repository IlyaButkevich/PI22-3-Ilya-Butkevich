package com.example.dstore;

import java.util.Base64;
import java.util.Date;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;


//-- SELECT DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS
//        --   WHERE table_name = 'goods' AND COLUMN_NAME = 'image';
//        -- alter table goods modify image longblob;


@Entity
public class Goods {
    private Long id;
    private String goodname;
    private String description;
    private int quantity;
    @Lob
    private byte[] image;

    private String goodtype;

    private int price;
    protected Goods(){}


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public  void setId(Long id){this.id = id;}
    public String getGoodname(){return goodname;}
    public  void setGoodname(String goodname){this.goodname = goodname;}
    public String getDescription(){return description;}
    public  void setDescription(String description){this.description = description;}
    public int getQuantity(){return quantity;}
    public  void setQuantity(int quantity){this.quantity = quantity;}
    public byte[] getImage(){return image;}
    public  void setImage(byte[] image){this.image = image;}
    public String getGoodtype(){return goodtype;}
    public  void setGoodtype(String goodtype){this.goodtype = goodtype;}

    @Transient
    public String getImageBase64() {
        if (image != null) {
            return Base64.getEncoder().encodeToString(image);
        }
        return null;
    }

    public int getPrice(){return price;}
    public  void setPrice(int price){this.price = price;}
}
