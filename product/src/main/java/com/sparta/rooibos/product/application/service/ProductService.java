package com.sparta.rooibos.product.application.service;

import com.sparta.rooibos.product.application.dto.request.CreateProductRequest;
import com.sparta.rooibos.product.application.dto.request.SearchProductRequest;
import com.sparta.rooibos.product.application.dto.request.UpdateProductRequest;
import com.sparta.rooibos.product.application.dto.response.CreateProductResponse;
import com.sparta.rooibos.product.application.dto.response.GetProductResponse;
import com.sparta.rooibos.product.application.dto.response.SearchProductListResponse;
import com.sparta.rooibos.product.application.dto.response.SearchProductResponse;
import com.sparta.rooibos.product.domain.entity.Product;
import com.sparta.rooibos.product.domain.repository.ProductRepository;
import com.sparta.rooibos.product.domain.repository.QueryProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final QueryProductRepository queryProductRepository;


    public SearchProductResponse getProductList(SearchProductRequest request) {
        Page<Product> products = queryProductRepository.getProductList(request.pageable(),request.id(),request.name());

        return new SearchProductResponse(products.getContent().stream().map(
                p -> new SearchProductListResponse(p.getId(), p.getName(), p.getClientId(), p.getManagedHubId())).toList(),
                products.getTotalElements(), products.getNumber() + 1, products.getSize());
    }

    public GetProductResponse getProduct(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new IllegalArgumentException("제공하는 상품이 존재하지 않습니다."));
        return new GetProductResponse(product);
    }

    public CreateProductResponse createProduct(CreateProductRequest request) {

        // 상품 등록
        Product product = productRepository.save(new Product(
                request.name(),
                request.clientId(),
                request.managedHubId(),
                "계정 아이디"
        ));


        return new CreateProductResponse(product.getId(), request.name());
    }

    @Transactional
    public boolean updateProduct(UpdateProductRequest request) {
        UUID id = request.productId();
        final Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("제공하는 상품이 존재하지 않습니다."));
        // 상품 수정
        product.update(request.name());
        return true;
    }

    @Transactional
    public boolean deleteProduct(UUID id) {
        final Product product = productRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("제공하는 상품이 존재하지 않습니다."));
        product.delete("계정아이디");
        return true;
    }
}
