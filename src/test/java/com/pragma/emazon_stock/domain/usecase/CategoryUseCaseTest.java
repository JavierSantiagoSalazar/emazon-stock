package com.pragma.emazon_stock.domain.usecase;

import com.pragma.emazon_stock.domain.exceptions.CategoryAlreadyExistsException;
import com.pragma.emazon_stock.domain.exceptions.NoContentCategoryException;
import com.pragma.emazon_stock.domain.exceptions.PageOutOfBoundsException;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.model.Pagination;
import com.pragma.emazon_stock.domain.spi.CategoryPersistencePort;
import com.pragma.emazon_stock.utils.ModelsTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryUseCaseTest {

    @Mock
    private CategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    private Category defaultCategory;

    @BeforeEach
    public void setUp() {
        defaultCategory = ModelsTestFactory.createDefaultCategory();
    }

    @Test
    void givenCategoryDoesNotExist_whenSaveCategoryIsCalled_thenCategoryIsSaved() {
        when(categoryPersistencePort.checkIfCategoryExists(defaultCategory.getName())).thenReturn(false);

        categoryUseCase.saveCategory(defaultCategory);

        verify(categoryPersistencePort, times(1)).checkIfCategoryExists(defaultCategory.getName());
        verify(categoryPersistencePort, times(1)).saveCategory(defaultCategory);

    }

    @Test
    void givenCategoryAlreadyExists_whenSaveCategoryIsCalled_thenThrowsException() {
        when(categoryPersistencePort.checkIfCategoryExists(defaultCategory.getName())).thenReturn(true);

        assertThrows(CategoryAlreadyExistsException.class, () -> categoryUseCase.saveCategory(defaultCategory));

        verify(categoryPersistencePort, times(1)).checkIfCategoryExists(defaultCategory.getName());
        verify(categoryPersistencePort, never()).saveCategory(defaultCategory);

    }

    @Test
    void whenGetCategories_ThenReturnCategoriesWithSuccessfulPagination() {
        List<Category> categories = Arrays.asList(
                new Category(1,"Category 1", "Description 1"),
                new Category(2,"Category 2", "Description 2"),
                new Category(3,"Category 3", "Description 3")
        );

        when(categoryPersistencePort.getAllCategories()).thenReturn(categories);

        Pagination<Category> result = categoryUseCase.getCategories("asc", 1, 2);

        assertEquals(2, result.getItems().size());
        assertEquals(3, result.getTotalItems());
        assertEquals(2, result.getTotalPages());
        assertFalse(result.getIsLastPage());
        verify(categoryPersistencePort, times(1)).getAllCategories();

    }

    @Test
    void givenCategories_whenGetCategories_ThenReturnsNoContent() {

        when(categoryPersistencePort.getAllCategories()).thenReturn(Collections.emptyList());

        assertThrows(NoContentCategoryException.class, () -> {
            categoryUseCase.getCategories("asc", 1, 2);
        });

    }

    @Test
    void givenCategoriesAndLargerPage_whenGetCategories_thenReturnsPageOutOfBounds() {

        List<Category> categories = Arrays.asList(
                new Category(2,"Category 2", "Description 2"),
                new Category(1,"Category 1", "Description 1"),
                new Category(3,"Category 3", "Description 3")
        );

        when(categoryPersistencePort.getAllCategories()).thenReturn(categories);

        assertThrows(PageOutOfBoundsException.class, () -> {
            categoryUseCase.getCategories("asc", 5, 2);
        });

    }

    @Test
    void givenCategoriesAndDescOrder_whenGetCategories_thenReturnsCategoriesListInDescendingOrder() {

        List<Category> categories = Arrays.asList(
                new Category(1, "Category 1", "Description 1"),
                new Category(2, "Category 2", "Description 2"),
                new Category(3, "Category 3", "Description 3")
        );

        when(categoryPersistencePort.getAllCategories()).thenReturn(categories);

        Pagination<Category> result = categoryUseCase.getCategories("desc", 1, 2);

        assertEquals("Category 3", result.getItems().get(0).getName());
        assertEquals("Category 2", result.getItems().get(1).getName());
        verify(categoryPersistencePort, times(1)).getAllCategories();

    }

}
