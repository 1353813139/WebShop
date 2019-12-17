package com.example.demo.domain;

import javax.persistence.*;

@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private Double price;
    private int sell;
    private Double sellamout;
    private int num;

    public Report(String name, Double price, int sell, Double sellamout,int num) {
        this.name = name;
        this.price = price;
        this.sell = sell;
        this.sellamout = sellamout;
        this.num = num;
    }
    public Report(){

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

    public int getSell() {
        return sell;
    }

    public void setSell(int sell) {
        this.sell = sell;
    }

    public Double getSellamout() {
        return sellamout;
    }

    public void setSellamout(Double sellamout) {
        this.sellamout = sellamout;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
