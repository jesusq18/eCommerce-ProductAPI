package com.javiandjesus.Cart.Ecommerce.domain;


public class Product {
    private Integer id;
    private String title;
    private Double price;
    private String brand;
    private String category;

    public Product(Integer id, String title, Double price, String brand, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.brand = brand;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
