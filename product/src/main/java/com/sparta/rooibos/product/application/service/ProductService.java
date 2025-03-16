package com.sparta.rooibos.product.application.service;

import com.sparta.rooibos.product.application.dto.request.CreateProductApplicationRequest;
import com.sparta.rooibos.product.application.dto.response.CreateProductApplicationResponse;
import com.sparta.rooibos.product.application.dto.response.GetProductApplicationResponse;
import com.sparta.rooibos.product.domain.entity.Product;
import com.sparta.rooibos.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public void getProductList() {

    }

    public GetProductApplicationResponse getProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("제공하는 상품이 존재하지 않습니다."));
        return new GetProductApplicationResponse(product);

    }

    public CreateProductApplicationResponse createProduct(CreateProductApplicationRequest request) {

        // 상품 등록
        Product product = productRepository.save(new Product(
                request.name(),
                request.clientId(),
                request.managedHubId(),
                "계정 아이디"
        ));


        return new CreateProductApplicationResponse(product.getId(), request.name());
    }

    public void updateProduct(String productId) {

    }

    public void deleteProduct(String productId) {

    }
}
