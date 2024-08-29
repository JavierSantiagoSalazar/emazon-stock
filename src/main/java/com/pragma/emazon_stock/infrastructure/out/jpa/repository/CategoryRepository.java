package com.pragma.emazon_stock.infrastructure.out.jpa.repository;

import com.pragma.emazon_stock.infrastructure.out.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    Optional<CategoryEntity> findByName(String categoryName);

    List<CategoryEntity> findByNameIn(List<String> categoryName);

}
