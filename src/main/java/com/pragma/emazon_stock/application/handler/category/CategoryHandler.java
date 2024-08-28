package com.pragma.emazon_stock.application.handler.category;

import com.pragma.emazon_stock.application.dto.category.CategoryRequest;
import com.pragma.emazon_stock.application.dto.category.CategoryResponse;
import com.pragma.emazon_stock.domain.model.Pagination;

public interface CategoryHandler {

    void createCategory(CategoryRequest categoryRequest);

    Pagination<CategoryResponse> getCategories(String sortOrder, Integer page, Integer size);

}
