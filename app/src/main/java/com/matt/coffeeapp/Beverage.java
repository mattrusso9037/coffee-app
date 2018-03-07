package com.matt.coffeeapp;

/**
 * Created by mattr on 2/6/2018.
 */

public class Beverage {

public Beverage(){

}

    private String name;
    private String size;
    private double price;

    public Beverage(String name, String size, double price) {
        this.name = name;
        this.size = size;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
