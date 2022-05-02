package com.narval.stockms.model;

public class IncomingPurchase {
    private int id;
    private int supplier_id;
    private int product_id;
    private int number_received;
    private String purchase_date;

    public IncomingPurchase(int id, int supplier_id, int product_id, int number_received, String purchase_date) {
        this.id = id;
        this.supplier_id = supplier_id;
        this.product_id = product_id;
        this.number_received = number_received;
        this.purchase_date = purchase_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getNumber_received() {
        return number_received;
    }

    public void setNumber_received(int number_received) {
        this.number_received = number_received;
    }

    public String getPurchase_date() {
        return purchase_date;
    }

    public void setPurchase_date(String purchase_date) {
        this.purchase_date = purchase_date;
    }
}
