package com.pragma.emazon_stock.application.handler;

import com.pragma.emazon_stock.application.dto.CategoryRequest;
import com.pragma.emazon_stock.application.dto.CategoryResponse;
import com.pragma.emazon_stock.domain.model.Pagination;

public interface CategoryHandler {

    void createCategory(CategoryRequest categoryRequest);

    Pagination<CategoryResponse> getCategories(String sortOrder, Integer page, Integer size);

}
