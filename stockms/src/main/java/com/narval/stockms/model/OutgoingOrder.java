package com.narval.stockms.model;

public class OutgoingOrder {
    private int id;
    private int product_id;
    private int number_ordered;
    private String order_date;

    public OutgoingOrder(int id, int product_id, int number_ordered, String order_date) {
        this.id = id;
        this.product_id = product_id;
        this.number_ordered = number_ordered;
        this.order_date = order_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getNumber_ordered() {
        return number_ordered;
    }

    public void setNumber_ordered(int number_ordered) {
        this.number_ordered = number_ordered;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }
}
