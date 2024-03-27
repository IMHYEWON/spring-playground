package com.hyewon.springplayground.redis.repository;

import com.hyewon.springplayground.redis.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findByProductId(Long productId);
}
