package com.sparta.rooibos.product.presentation.controller;

import com.sparta.rooibos.product.application.annotation.RoleCheck;
import com.sparta.rooibos.product.application.dto.request.CreateProductRequest;
import com.sparta.rooibos.product.application.dto.request.SearchProductRequest;
import com.sparta.rooibos.product.application.dto.request.UpdateProductRequest;
import com.sparta.rooibos.product.application.dto.response.CreateProductResponse;
import com.sparta.rooibos.product.application.dto.response.GetProductResponse;
import com.sparta.rooibos.product.application.dto.response.SearchProductResponse;
import com.sparta.rooibos.product.application.service.ProductService;
import com.sparta.rooibos.product.application.type.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;


    @GetMapping
    @RoleCheck({Role.MASTER, Role.HUB, Role.DELIVERY, Role.CLIENT})
    public ResponseEntity<SearchProductResponse> getProductList(
            @RequestHeader(value = "X-User-Email") String email,
            @RequestHeader(value = "X-User-Name") String username,
            @RequestHeader(value = "X-User-Role") String role,
            @ModelAttribute SearchProductRequest request) {
        Pageable pageable = PageRequest.of(request.page() - 1, request.size(), Sort.by(Sort.Direction.DESC, request.sort()));
        return ResponseEntity.ok(productService.getProductList(request, pageable));
    }

    @GetMapping("/{productId}")
    @RoleCheck({Role.MASTER, Role.HUB, Role.DELIVERY, Role.CLIENT})
    public ResponseEntity<GetProductResponse> getProduct(
            @RequestHeader(value = "X-User-Email") String email,
            @RequestHeader(value = "X-User-Name") String username,
            @RequestHeader(value = "X-User-Role") String role,
            @PathVariable UUID productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @PostMapping
    @RoleCheck({Role.MASTER, Role.HUB, Role.CLIENT})
    public ResponseEntity<CreateProductResponse> createProduct(
            @RequestHeader(value = "X-User-Email") String email,
            @RequestHeader(value = "X-User-Name") String username,
            @RequestHeader(value = "X-User-Role") String role,
            @RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PutMapping("/{productId}")
    @RoleCheck({Role.MASTER, Role.HUB, Role.CLIENT})
    public ResponseEntity<Boolean> updateProduct(
            @RequestHeader(value = "X-User-Email") String email,
            @RequestHeader(value = "X-User-Name") String username,
            @RequestHeader(value = "X-User-Role") String role,
            @PathVariable UUID productId, @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(productId, request));
    }

    @PatchMapping("/{productId}")
    @RoleCheck({Role.MASTER, Role.HUB})
    public ResponseEntity<Boolean> deleteProduct(
            @RequestHeader(value = "X-User-Email") String email,
            @RequestHeader(value = "X-User-Name") String username,
            @RequestHeader(value = "X-User-Role") String role,
            @PathVariable UUID productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId));
    }

}
