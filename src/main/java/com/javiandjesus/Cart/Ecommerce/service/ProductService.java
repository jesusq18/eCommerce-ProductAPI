package com.javiandjesus.Cart.Ecommerce.service;

import com.javiandjesus.Cart.Ecommerce.domain.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    // Array of products

    List<Product> products = new ArrayList<Product>(Arrays.asList(
            new Product(1, "Xiaomi Poco X6", 699.99, "Xiaomi", "Smartphone"),
            new Product(2, "Book 4 Ultra", 1299.99, "Apple", "Laptop"),
            new Product(3, "Fit 4", 199.99, "Apple","Smartwatch"),
            new Product(4, "Buds T5", 89.99, "JBL", "Bluetooth Headphones"),
            new Product(5, "A9", 399.99, "Samsung","Tablet"),
            new Product(6, "PS5 Pro", 499.99, "Sony","Gaming Console")
    ));

    // Implementation CRUD methods

    public List<Product> getProducts(){
        return products;
    }

    public Optional<Product> getProductById(Integer id) {
        return products.stream()
                .filter(product -> product.getId().equals(id))
                .findFirst();
    }

    public void addProduct(Product product) {
        if (products.stream().anyMatch(p -> p.getId().equals(product.getId()))) {
            throw new RuntimeException("Product with this ID already exists");
        }
        products.add(product);
    }

    public Product updateProduct(Product product) {
        for (Product p : products){
            if(p.getId().equals(product.getId())){
                p.setTitle(product.getTitle());
                p.setPrice(product.getPrice());
                p.setBrand(product.getBrand());
                p.setCategory(product.getCategory());
                return p;
            }
        }
        throw new RuntimeException("Product not found");
    }

    public void deleteProduct(Integer id) {
        if(products.stream().anyMatch(p -> p.getId().equals(id))){
        products.removeIf(product -> product.getId().equals(id));
        } else {
            throw new RuntimeException("Product not found");
        }
    }

    public boolean productExists(Product product) {
        return getProductById(product.getId()).isPresent();  // Verifica si el ID ya existe
    }



}

