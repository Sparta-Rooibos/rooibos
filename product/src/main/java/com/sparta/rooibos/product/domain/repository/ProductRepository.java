package com.sparta.rooibos.product.domain.repository;

import com.sparta.rooibos.product.domain.entity.Product;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository {

    Optional<Product> findById(UUID id);

    Product save(Product product);
}
