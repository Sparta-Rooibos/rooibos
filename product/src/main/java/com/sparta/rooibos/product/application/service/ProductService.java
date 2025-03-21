package com.sparta.rooibos.product.application.service;

import com.sparta.rooibos.product.application.dto.request.CreateProductRequest;
import com.sparta.rooibos.product.application.dto.request.SearchProductRequest;
import com.sparta.rooibos.product.application.dto.request.UpdateProductRequest;
import com.sparta.rooibos.product.application.dto.response.CreateProductResponse;
import com.sparta.rooibos.product.application.dto.response.GetProductResponse;
import com.sparta.rooibos.product.application.dto.response.SearchProductListResponse;
import com.sparta.rooibos.product.application.dto.response.SearchProductResponse;
import com.sparta.rooibos.product.application.exception.BusinessProductException;
import com.sparta.rooibos.product.application.exception.ProductErrorCode;
import com.sparta.rooibos.product.domain.entity.Product;
import com.sparta.rooibos.product.domain.feign.dto.Client;
import com.sparta.rooibos.product.domain.feign.service.ClientService;
import com.sparta.rooibos.product.domain.repository.ProductRepository;
import com.sparta.rooibos.product.domain.repository.QueryProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final QueryProductRepository queryProductRepository;
    private final ClientService clientService;

    public SearchProductResponse getProductList(SearchProductRequest request, Pageable pageable) {
        Page<Product> products = queryProductRepository.getProductList(pageable, request.id(), request.name(), request.isDeleted());

        return new SearchProductResponse(products.getContent().stream().map(
                p -> new SearchProductListResponse(p.getId(), p.getName(), p.getClientId(), p.getManagedHubId(), p.getDeleteBy() != null)).toList(),
                products.getTotalElements(), products.getNumber() + 1, products.getSize());
    }

    public GetProductResponse getProduct(UUID productId) {
        Product product = productRepository.findByIdAndDeleteByIsNull(productId).orElseThrow(() -> new BusinessProductException(ProductErrorCode.NOT_FOUND_PRODUCT));
        return new GetProductResponse(product);
    }

    public CreateProductResponse createProduct(CreateProductRequest request) {
        if (productRepository.findByNameAndDeleteByIsNull(request.name()).isPresent()) {
            throw new BusinessProductException(ProductErrorCode.NOT_EXITS_PRODUCT);
        }

        Client client = clientService.getClient(request.clientId());

        // 수령 업체는 제품 생산이 불가능합니다.
        if(client.type().equals("CONSUME")) {
            throw new BusinessProductException(ProductErrorCode.NOT_SUPPORTED_TYPE);
        }

        // 상품 등록
        Product product = productRepository.save(new Product(
                request.name(),
                client.id(),
                client.manageHub().id(),
                "계정 아이디"
        ));

        return new CreateProductResponse(product.getId(), request.name());
    }

    @Transactional
    public boolean updateProduct(UUID id, UpdateProductRequest request) {
        //아이디가 같은 경우에는 무시한다.
        if (productRepository.findByNameAndDeleteByIsNull(request.name()).filter(
                product -> !product.getId().equals(id)
        ).isPresent()) {
            throw new BusinessProductException(ProductErrorCode.NOT_EXITS_PRODUCT);
        }

        final Product product = productRepository.findByIdAndDeleteByIsNull(id).orElseThrow(() -> new BusinessProductException(ProductErrorCode.NOT_FOUND_PRODUCT));
        // 상품 수정
        product.update(request.name());
        return true;
    }

    @Transactional
    public boolean deleteProduct(UUID id) {
        final Product product = productRepository.findByIdAndDeleteByIsNull(id).orElseThrow(() -> new BusinessProductException(ProductErrorCode.NOT_FOUND_PRODUCT));
        product.delete("계정아이디");
        return true;
    }
}
