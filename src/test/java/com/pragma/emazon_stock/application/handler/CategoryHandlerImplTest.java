package com.pragma.emazon_stock.application.handler;

import com.pragma.emazon_stock.application.dto.category.CategoryRequest;
import com.pragma.emazon_stock.application.dto.category.CategoryResponse;
import com.pragma.emazon_stock.application.handler.category.CategoryHandlerImpl;
import com.pragma.emazon_stock.application.mappers.category.CategoryRequestMapper;
import com.pragma.emazon_stock.application.mappers.category.CategoryResponseMapper;
import com.pragma.emazon_stock.domain.api.CategoryServicePort;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.model.Pagination;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryHandlerImplTest {

    @Mock
    private CategoryServicePort categoryServicePort;

    @Mock
    private CategoryRequestMapper categoryRequestMapper;

    @Mock
    private CategoryResponseMapper categoryResponseMapper;

    @InjectMocks
    private CategoryHandlerImpl categoryHandlerImpl;

    @Test
    void givenValidCategoryRequest_whenCreateCategory_thenCategoryIsSaved() {

        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName(" Home ");
        categoryRequest.setDescription("  All things for the home  ");

        Category mappedCategory = new Category(null, "HOME", "All things for the home");

        when(categoryRequestMapper.toDomain(categoryRequest)).thenReturn(mappedCategory);

        categoryHandlerImpl.createCategory(categoryRequest);

        verify(categoryRequestMapper,times(1)).toDomain(categoryRequest);
        verify(categoryServicePort, times(1)).saveCategory(mappedCategory);

    }

    @Test
    void givenValidRequest_whenGetCategories_thenReturnsCategoriesList() {

        String sortOrder = "asc";
        Integer page = 1;
        Integer size = 10;

        Category category = new Category(1,"HOME", "All things related with Home");
        List<Category> categoryList = List.of(category);

        Pagination<Category> pagination = new Pagination<>(
                categoryList,
                page,
                size,
                1L,
                1,
                true
        );

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setName("HOME");
        categoryResponse.setDescription("All things related with Home");

        when(categoryServicePort.getCategories(sortOrder, page, size)).thenReturn(pagination);
        when(categoryResponseMapper.toResponse(category)).thenReturn(categoryResponse);

        Pagination<CategoryResponse> result = categoryHandlerImpl.getCategories(sortOrder, page, size);

        assertEquals(1, result.getItems().size());
        assertEquals("HOME", result.getItems().get(0).getName());
        assertEquals(page, result.getPageNo());
        assertEquals(size, result.getPageSize());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.getIsLastPage());

        verify(categoryServicePort, times(1)).getCategories(sortOrder, page, size);
        verify(categoryResponseMapper, times(1)).toResponse(category);

    }

}
