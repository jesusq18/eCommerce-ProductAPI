package com.javiandjesus.Cart.Ecommerce.controllers;

import com.javiandjesus.Cart.Ecommerce.domain.Cart;
import com.javiandjesus.Cart.Ecommerce.domain.CartRequest;
import com.javiandjesus.Cart.Ecommerce.domain.Product;
import com.javiandjesus.Cart.Ecommerce.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    // Rest Controller for Cart operations

    @RequestMapping("/{id}")
    public ResponseEntity<?> getCartById(@PathVariable Integer id) {
        Optional<Cart> cart = cartService.getCartById(id);
        if (cart.isPresent()) {
            return ResponseEntity.ok(cart.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> addProductToCart(@RequestBody CartRequest cartRequest) {
        // Validar que los productos recibidos sean válidos
        if (!cartService.areProductsValid(cartRequest.getProducts())) {
            return ResponseEntity.badRequest().body("One or more products are invalid.");
        }

        // Si los productos son válidos, crear el carrito
        cartService.createCart(cartRequest.getId(), cartRequest.getProducts());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cartRequest.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCart(@PathVariable Integer id, @RequestBody Cart cart) {
        Optional<Cart> cartOpt = cartService.getCartById(id);

        if (cartOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Validar que los productos recibidos son válidos
        if (!cartService.areProductsValid(cart.getProducts())) {
            return ResponseEntity.badRequest().body("One or more products are invalid.");
        }

        // Reemplazar el carrito existente
        Cart existingCart = cartOpt.get();
        existingCart.setProducts(cart.getProducts());
        existingCart.setTotal_price(cartService.calculateTotalPrice(existingCart));

        return ResponseEntity.ok(existingCart);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCart(@PathVariable Integer id) {
        cartService.getCartById(id)
                .ifPresent(cart -> cartService.deleteCartById(cart.getId()));
        return ResponseEntity.noContent().build();
    }

    // Crearte controller to add a product to the cart
    @PatchMapping("/{id}/addProduct")
    public ResponseEntity<?> addProductToExistendCart(@PathVariable Integer id, @RequestBody Product product) {
        Optional<Cart> cartOpt = cartService.getCartById(id);

        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();

            // Validar si el producto ya está en el carrito
            if (cart.getProducts().stream().anyMatch(p -> p.getId().equals(product.getId()))) {
                return ResponseEntity.badRequest().body("Product already exists in the cart.");
            }

            // Agregar producto y recalcular precio
            cart.getProducts().add(product);
            cart.setTotal_price(cartService.calculateTotalPrice(cart));

            return ResponseEntity.ok(cart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
