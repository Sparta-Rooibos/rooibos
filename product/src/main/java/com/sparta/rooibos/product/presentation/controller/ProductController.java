package com.sparta.rooibos.product.presentation.controller;

import com.sparta.rooibos.product.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @GetMapping
    public void getProductList() {
        productService.getProductList();
    }
    @GetMapping("/{productId}")
    public void getProduct(@PathVariable String productId) {
        productService.getProduct(productId);
    }
    @PostMapping
    public void createProduct() {
        productService.createProduct();
    }

    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable String productId) {
        productService.updateProduct(productId);
    }
    @PatchMapping("/{productId}")
    public void deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
    }

}
