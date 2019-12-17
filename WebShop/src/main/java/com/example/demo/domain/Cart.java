package com.example.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String username;
    private String name;
    private int amout;
    private Double price;
    private Double total;
    private String image;

    public Cart(String username, String name, int amout, Double price, Double total,String image) {
        this.username = username;
        this.name = name;
        this.amout = amout;
        this.price = price;
        this.total = total;
        this.image = image;
    }

    public Cart(String username, String name, Double price,String image) {
        this.username = username;
        this.name = name;
        this.amout = 1;
        this.price = price;
        this.image = image;
        this.total = this.amout * this.price;
    }
    public Cart(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmout() {
        return amout;
    }

    public void setAmout(int amout) {
        this.amout = amout;
        this.total = amout * this.price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
