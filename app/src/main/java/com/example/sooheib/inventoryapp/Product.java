package com.example.sooheib.inventoryapp;


/**
 * Created by sooheib on 8/27/16.
 */
public class Product {
    private int mId;
    private String mProduct;
    private int mQuantity;
    private double mprice;

    public Product() {
        super();
    }

    public Product(String product, int quantity, double price) {
        mProduct = product;
        mQuantity = quantity;
        mprice = price;

    }

    public int getmId() {
        return mId;
    }

    public String getmProduct() {
        return mProduct;
    }

    public double getmPrice() {
        return mprice;
    }

    public int getmQuantity() {
        return mQuantity;
    }

    public void setmProduct(String name) {
        mProduct = name;
    }

    public void setmPrice(double price) {
        mprice = price;
    }

    public void setmQuantity(int quantity) {
        mQuantity = quantity;
    }

    public void setmID(int id) { mId = id; }

    public void quantitySale() {
        mQuantity -= 1;
        if (mQuantity < 0) {
            mQuantity = 0;
        }
    }

    @Override
    public String toString() {
        return getmProduct() + " " + getmPrice() + " " + getmQuantity();
    }
}
