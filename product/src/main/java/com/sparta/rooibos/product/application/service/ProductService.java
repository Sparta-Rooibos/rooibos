package com.sparta.rooibos.product.application.service;

import com.sparta.rooibos.product.application.dto.req.CreateProductApplicationRequest;
import com.sparta.rooibos.product.application.dto.res.CreateProductApplicationResponse;
import com.sparta.rooibos.product.domain.entity.Product;
import com.sparta.rooibos.product.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;


    public void getProductList() {

    }

    public void getProduct(String productId) {

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
