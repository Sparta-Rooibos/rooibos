package com.sparta.rooibos.product.domain.repository;

import com.sparta.rooibos.product.domain.entity.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository {
    Product save(Product product);
}
