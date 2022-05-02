package com.narval.stockms.model;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private int minRequired;


    public Product(int id, String name, int quantity, int minRequired) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.minRequired = minRequired;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMinRequired() {
        return minRequired;
    }

    public void setMinRequired(int minRequired) {
        this.minRequired = minRequired;
    }
}
