package com.sparta.rooibos.product.presentation.controller;

import com.sparta.rooibos.product.application.service.ProductService;
import com.sparta.rooibos.product.presentation.dto.request.CreateProductRequestDto;
import com.sparta.rooibos.product.presentation.dto.request.SearchProductRequestDto;
import com.sparta.rooibos.product.presentation.dto.request.UpdateProductRequestDto;
import com.sparta.rooibos.product.presentation.dto.response.CreateProductResponseDto;
import com.sparta.rooibos.product.presentation.dto.response.GetProductResponseDto;
import com.sparta.rooibos.product.presentation.dto.response.SearchProductResponseDto;
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
    public SearchProductResponseDto getProductList(@ModelAttribute SearchProductRequestDto request) {
        Pageable pageable = PageRequest.of(request.page() - 1, request.size(), Sort.by(Sort.Direction.DESC, request.sort()));
        return new SearchProductResponseDto(productService.getProductList(request.toApplication(pageable)));
    }

    @GetMapping("/{productId}")
    public GetProductResponseDto getProduct(@PathVariable UUID productId) {
        return new GetProductResponseDto(productService.getProduct(productId));
    }

    @PostMapping
    public CreateProductResponseDto createProduct(@RequestBody CreateProductRequestDto request) {
        return new CreateProductResponseDto(productService.createProduct(request.toApplication()));
    }

    @PutMapping("/{productId}")
    public boolean updateProduct(@PathVariable UUID productId, @RequestBody UpdateProductRequestDto request) {
        return productService.updateProduct(request.toApplication(productId));
    }

    @PatchMapping("/{productId}")
    public boolean deleteProduct(@PathVariable UUID productId) {
        return productService.deleteProduct(productId);
    }

}
