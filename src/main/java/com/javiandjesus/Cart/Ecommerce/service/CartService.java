package com.javiandjesus.Cart.Ecommerce.service;

import com.javiandjesus.Cart.Ecommerce.domain.Cart;
import com.javiandjesus.Cart.Ecommerce.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final ProductService productService;
    private List<Cart> carts = new ArrayList<>();

    @Autowired
    public CartService(ProductService productService) {
        this.productService = productService;  // Inyección de ProductService
    }

    public double calculateTotalPrice(Cart cart) {
        double totalPrice = cart.getProducts().stream()
                .mapToDouble(Product::getPrice)
                .sum();  // Suma los precios de todos los productos en el carrito

        BigDecimal roundedPrice = new BigDecimal(totalPrice).setScale(2,RoundingMode.HALF_UP);
                return roundedPrice.doubleValue();
    }

    public void createCart(Integer id, List<Product> products) {
        // Valida que los productos sean válidos antes de crear el carrito
        if (!areProductsValid(products)) {
            throw new IllegalArgumentException("One or more products are invalid.");
        }

        Cart cart = new Cart();
        cart.setId(id);
        cart.setProducts(products);  // envia el parametro a la lista de productos
        cart.setTotal_price(calculateTotalPrice(cart));
        carts.add(cart);
    }


    public boolean areProductsValid(List<Product> receivedProducts) {
        List<Product> validProducts = productService.getProducts();  // Obtén la lista de productos válidos

        // Verifica que todos los productos recibidos existan en la lista de productos válidos
        return receivedProducts.stream()
                .allMatch(receivedProduct -> validProducts.stream()
                        .anyMatch(validProduct ->
                                validProduct.getId().equals(receivedProduct.getId()) &&
                                        validProduct.getTitle().equals(receivedProduct.getTitle()) &&
                                        validProduct.getPrice().equals(receivedProduct.getPrice()) &&
                                        validProduct.getBrand().equals(receivedProduct.getBrand()) &&
                                        validProduct.getCategory().equals(receivedProduct.getCategory())));
    }


    public Optional<Cart> getCartById(Integer id) {
        return carts.stream()  // Inicia el stream sobre la lista de carritos
                .filter(c -> c.getId().equals(id))  // Filtra los carritos por ID
                .findFirst(); // Encuentra el primer carrito que coincida
    }

    public void deleteCartById(Integer id){
        Optional<Cart> cartOpt = getCartById(id);
        cartOpt.ifPresent(carts::remove);  // Elimina el carrito si está presente
    }

}




