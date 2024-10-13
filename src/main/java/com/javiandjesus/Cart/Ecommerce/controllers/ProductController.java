package com.javiandjesus.Cart.Ecommerce.controllers;

import com.javiandjesus.Cart.Ecommerce.domain.Product;
import com.javiandjesus.Cart.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.awt.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getProductByID(@PathVariable Integer id) {
        Optional<Product> productOPT = productService.getProductById(id);
        return productOPT.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        if (productService.productExists(product)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Product already exists");
        }
        productService.addProduct(product);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(product.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody Product product) {
        Optional<Product> productOPT = productService.getProductById(product.getId());
        if (productOPT.isPresent()) {
            productService.updateProduct(product);
            return ResponseEntity.ok().body("Product updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping
    public ResponseEntity<?> modifyProduct(@RequestBody Product product) {
        Optional<Product> productOPT = productService.getProductById(product.getId());
        if (productOPT.isPresent()) {
            productService.updateProduct(product);
            return ResponseEntity.ok().body("Product with id: " + product.getId() + " has been updated");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This product doesn't exist");
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable Integer id) {
        Optional<Product> productOptional = productService.getProductById(id);
        if (productOptional.isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.ok().body("Product with id: " + id + " has been deleted");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This product doesn't exist");
        }
    }

}
