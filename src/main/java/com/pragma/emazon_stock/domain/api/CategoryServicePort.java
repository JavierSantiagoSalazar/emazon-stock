package com.pragma.emazon_stock.domain.api;

import com.pragma.emazon_stock.domain.model.Category;

public interface CategoryServicePort {

    void saveCategory(Category category);

    boolean checkIfCategoryExists(String categoryName);

}
