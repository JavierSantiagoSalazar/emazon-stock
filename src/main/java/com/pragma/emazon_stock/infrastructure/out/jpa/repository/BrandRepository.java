package com.pragma.emazon_stock.infrastructure.out.jpa.repository;

import com.pragma.emazon_stock.infrastructure.out.jpa.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<BrandEntity, Integer> {

    Optional<BrandEntity> findByBrandName(String brandName);

}
