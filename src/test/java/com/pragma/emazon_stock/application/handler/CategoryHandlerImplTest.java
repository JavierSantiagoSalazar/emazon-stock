package com.pragma.emazon_stock.application.handler;

import com.pragma.emazon_stock.application.dto.CategoryRequest;
import com.pragma.emazon_stock.application.mappers.CategoryRequestMapper;
import com.pragma.emazon_stock.domain.api.CategoryServicePort;
import com.pragma.emazon_stock.domain.model.Category;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryHandlerImplTest {

    @Mock
    private CategoryServicePort categoryServicePort;

    @Mock
    private CategoryRequestMapper categoryRequestMapper;

    @InjectMocks
    private CategoryHandlerImpl categoryHandlerImpl;

    @Test
    void givenValidCategoryRequest_whenCreateCategory_thenCategoryIsSaved() {
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setName(" Home ");
        categoryRequest.setDescription("  All things for the home  ");

        Category mappedCategory = new Category(null, "HOME", "All things for the home");

        when(categoryRequestMapper.categotyRequestToDomainCategory(categoryRequest)).thenReturn(mappedCategory);

        categoryHandlerImpl.createCategory(categoryRequest);

        verify(categoryRequestMapper).categotyRequestToDomainCategory(categoryRequest);
        verify(categoryServicePort).saveCategory(mappedCategory);
    }

}
