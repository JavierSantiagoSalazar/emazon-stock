package com.pragma.emazon_stock.utils;

import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.BrandEntity;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.CategoryEntity;

public class ModelsTestFactory {

    public static Category createDefaultCategory() {
        return new Category(1, "HOME", "All things for the home");
    }

    public static CategoryEntity createDefaultCategoryEntity() {
        return new CategoryEntity(1, "HOME", "All things for the home");
    }

    public static Brand createDefaultBrand() {
        return new Brand(1, "NOKIA", "All nokia tech");
    }

    public static BrandEntity createDefaultBrandEntity() {
        return new BrandEntity(1, "NOKIA", "All nokia tech");
    }

}
