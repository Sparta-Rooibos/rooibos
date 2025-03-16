package com.sparta.rooibos.product.presentation.controller;

import com.sparta.rooibos.product.application.service.ProductService;
import com.sparta.rooibos.product.presentation.dto.request.CreateProductRequest;
import com.sparta.rooibos.product.presentation.dto.request.UpdateProductRequest;
import com.sparta.rooibos.product.presentation.dto.response.CreateProductResponse;
import com.sparta.rooibos.product.presentation.dto.response.GetProductResponse;
import com.sparta.rooibos.product.presentation.dto.response.SearchProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;


    @GetMapping
    public SearchProductResponse getProductList() {
        return new SearchProductResponse(productService.getProductList());
    }

    @GetMapping("/{productId}")
    public GetProductResponse getProduct(@PathVariable UUID productId) {
        return new GetProductResponse(productService.getProduct(productId));
    }

    @PostMapping
    public CreateProductResponse createProduct(@RequestBody CreateProductRequest request) {
        return new CreateProductResponse(productService.createProduct(request.toApplication()));
    }

    @PutMapping("/{productId}")
    public boolean updateProduct(@PathVariable UUID productId, @RequestBody UpdateProductRequest request) {
        return productService.updateProduct(request.toApplication(productId));
    }

    @PatchMapping("/{productId}")
    public void deleteProduct(@PathVariable String productId) {
        productService.deleteProduct(productId);
    }

}
