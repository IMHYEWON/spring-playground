package com.hyewon.springplayground.cache.repository;

import com.hyewon.springplayground.cache.domain.CachedData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CachedDataRepository extends CrudRepository<CachedData, String> {
    
    Optional<CachedData> findByCacheKey(String cacheKey);
    
    void deleteByCacheKey(String cacheKey);
}
