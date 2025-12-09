package com.pluralsight.NorthwindTradersAPI.models;

public class Product {

    private int productId;
    private String productName;
    private String categoryName;
    private double unitPrice;

    public Product() {}

    public Product(int productId, String productName, String categoryName, double unitPrice) {
        this.productId = productId;
        this.productName = productName;
        this.categoryName = categoryName;
        this.unitPrice = unitPrice;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
}
