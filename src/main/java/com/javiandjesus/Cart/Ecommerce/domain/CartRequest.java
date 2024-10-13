package com.javiandjesus.Cart.Ecommerce.domain;

import java.util.List;

public class CartRequest {
    private Integer id;
    private List<Product> products;

    public CartRequest() {}

    public CartRequest(Integer id, List<Product> products) {
        this.id = id;
        this.products = products;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
