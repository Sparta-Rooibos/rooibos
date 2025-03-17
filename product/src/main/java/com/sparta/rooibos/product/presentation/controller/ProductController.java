package com.sparta.rooibos.product.presentation.controller;

import com.sparta.rooibos.product.application.dto.request.CreateProductRequest;
import com.sparta.rooibos.product.application.dto.request.SearchProductRequest;
import com.sparta.rooibos.product.application.dto.request.UpdateProductRequest;
import com.sparta.rooibos.product.application.dto.response.CreateProductResponse;
import com.sparta.rooibos.product.application.dto.response.GetProductResponse;
import com.sparta.rooibos.product.application.dto.response.SearchProductResponse;
import com.sparta.rooibos.product.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;


    @GetMapping
    public SearchProductResponse getProductList(@ModelAttribute SearchProductRequest request) {
        Pageable pageable = PageRequest.of(request.page() - 1, request.size(), Sort.by(Sort.Direction.DESC, request.sort()));
        return productService.getProductList(request, pageable);
    }

    @GetMapping("/{productId}")
    public GetProductResponse getProduct(@PathVariable UUID productId) {
        return productService.getProduct(productId);
    }

    @PostMapping
    public CreateProductResponse createProduct(@RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @PutMapping("/{productId}")
    public boolean updateProduct(@PathVariable UUID productId, @RequestBody UpdateProductRequest request) {
        return productService.updateProduct(productId, request);
    }

    @PatchMapping("/{productId}")
    public boolean deleteProduct(@PathVariable UUID productId) {
        return productService.deleteProduct(productId);
    }

}
