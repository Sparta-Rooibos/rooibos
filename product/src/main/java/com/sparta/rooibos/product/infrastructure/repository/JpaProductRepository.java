package com.sparta.rooibos.product.infrastructure.repository;

import com.sparta.rooibos.product.domain.entity.Product;
import com.sparta.rooibos.product.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product, String>, ProductRepository {
}
