package com.example.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "good")
public class Good {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private Double price;
    private String tag;
    private Double rrp;
    private int num;
    private String introduction;
    private String image;

    public Good(String name, Double price, String tag, Double rrp, int num, String introduction, String image) {
        this.name = name;
        this.price = price;
        this.tag = tag;
        this.rrp = rrp;
        this.num = num;
        this.introduction = introduction;
        this.image = image;
    }

    public Good(String name, Double price, String tag, int num, String introduction, String image) {
        this.name = name;
        this.price = price;
        this.tag = tag;
        this.num = num;
        this.introduction = introduction;
        this.image = image;
    }
    public Good(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Double getRrp() {
        return rrp;
    }

    public void setRrp(Double rrp) {
        this.rrp = rrp;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
