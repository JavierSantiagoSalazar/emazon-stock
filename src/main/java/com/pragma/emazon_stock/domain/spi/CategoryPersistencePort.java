package com.pragma.emazon_stock.domain.spi;

import com.pragma.emazon_stock.domain.model.Category;

import java.util.List;

public interface CategoryPersistencePort {

    void saveCategory(Category category);

    Boolean checkIfCategoryExists(String categoryName);

    List<Category> getAllCategories();

    List<Category> getAllCategoriesByName(List<String> categoriesName);

}
