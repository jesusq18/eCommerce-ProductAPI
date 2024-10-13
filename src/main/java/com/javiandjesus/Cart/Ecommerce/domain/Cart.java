package com.javiandjesus.Cart.Ecommerce.domain;

import java.util.List;

public class Cart {

    private Integer id;
    private double total_price;
    private List<Product> products;

    public Cart() {
    }

    public Cart(Integer id, List<Product> products) {
        this.id = id;
        this.products = products;
    }

    public Cart(Integer id, double total_price, List<Product> products) {
        this.id = id;
        this.total_price = 0.00;
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getTotal_price() {
        return total_price;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
