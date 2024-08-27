package com.pragma.emazon_stock.domain.api;

import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.model.Pagination;

public interface CategoryServicePort {

    void saveCategory(Category category);

    boolean checkIfCategoryExists(String categoryName);

    Pagination<Category> getCategories(String sortOrder, Integer page, Integer size);

}
