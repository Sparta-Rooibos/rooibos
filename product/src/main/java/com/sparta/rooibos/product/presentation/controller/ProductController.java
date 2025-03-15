package com.sparta.rooibos.product.presentation.controller;

import com.sparta.rooibos.product.application.service.ProductService;
import com.sparta.rooibos.product.presentation.dto.req.CreateProductRequest;
import com.sparta.rooibos.product.presentation.dto.res.CreateProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
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
    public CreateProductResponse createProduct(@RequestBody CreateProductRequest request) {
        return new CreateProductResponse(productService.createProduct(request.toApplication()));
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
