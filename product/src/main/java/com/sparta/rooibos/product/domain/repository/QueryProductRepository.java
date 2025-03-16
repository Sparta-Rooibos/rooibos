package com.sparta.rooibos.product.domain.repository;

import com.sparta.rooibos.product.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QueryProductRepository {
    Page<Product> getProductList(Pageable pageable, UUID id, String name, Boolean deleted);
}
