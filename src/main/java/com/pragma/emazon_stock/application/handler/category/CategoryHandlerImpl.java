package com.pragma.emazon_stock.application.handler.category;

import com.pragma.emazon_stock.application.dto.CategoryRequest;
import com.pragma.emazon_stock.application.dto.CategoryResponse;
import com.pragma.emazon_stock.application.mappers.category.CategoryRequestMapper;
import com.pragma.emazon_stock.application.mappers.category.CategoryResponseMapper;
import com.pragma.emazon_stock.domain.api.CategoryServicePort;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.model.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class CategoryHandlerImpl implements CategoryHandler {

    private final CategoryServicePort categoryServicePort;
    private final CategoryRequestMapper categoryRequestMapper;
    private final CategoryResponseMapper categoryResponseMapper;

    @Override
    public void createCategory(CategoryRequest categoryRequest) {

        categoryRequest.setName(categoryRequest.getName().trim().toUpperCase());
        categoryRequest.setDescription(categoryRequest.getDescription().trim());

        categoryServicePort.saveCategory(categoryRequestMapper.toDomain(categoryRequest));

    }

    @Override
    public Pagination<CategoryResponse> getCategories(String sortOrder, Integer page, Integer size) {

        Pagination<Category> paginationCategories = categoryServicePort.getCategories(sortOrder, page, size);

        List<CategoryResponse> categoryResponses = paginationCategories.getItems().stream()
                .map(categoryResponseMapper::toResponse)
                .toList();

        return new Pagination<>(
                categoryResponses,
                paginationCategories.getPageNo(),
                paginationCategories.getPageSize(),
                paginationCategories.getTotalItems(),
                paginationCategories.getTotalPages(),
                paginationCategories.getIsLastPage()
        );

    }

}
