package com.pragma.emazon_stock.infrastructure.out.jpa.repository;

import com.pragma.emazon_stock.infrastructure.out.jpa.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {

    Optional<ArticleEntity> findByArticleName(String articleName);

}
