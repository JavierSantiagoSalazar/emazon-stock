package com.pragma.emazon_stock.utils;

import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.ArticleEntity;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.BrandEntity;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class ModelsTestFactory {

    public static Category createDefaultCategory() {
        return new Category(1, "HOME", "All things for the home");
    }

    public static CategoryEntity createDefaultCategoryEntity() {
        return new CategoryEntity(1, "HOME", "All things for the home");
    }

    public static Brand createDefaultBrand() {
        return new Brand(1, "NOKIA", "All nokia tech", new ArrayList<>());
    }

    public static BrandEntity createDefaultBrandEntity() {
        return new BrandEntity(1, "NOKIA", "All nokia tech", new ArrayList<>());
    }

    public static Article createDefaultArticle() {

        return new Article(
                1,
                "PC",
                "A good pc",
                1,
                10.0,
                new Brand(1, "HP", "HP items", null),
                List.of(new Category(1, "GAMING", "Gaming items"))
        );
    }

    public static ArticleEntity createDefaultArticleEntity() {
        return new ArticleEntity(
                1,
                "PC",
                "A good pc",
                1,
                10.0,
                new BrandEntity(1, "HP", "HP items", null),
                List.of(new CategoryEntity(1, "GAMING", "Gaming items"))
        );
    }

}
