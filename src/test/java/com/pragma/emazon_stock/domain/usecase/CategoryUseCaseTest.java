package com.pragma.emazon_stock.domain.usecase;

import com.pragma.emazon_stock.domain.exceptions.CategoryAlreadyExistsException;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.spi.CategoryPersistencePort;
import com.pragma.emazon_stock.utils.CategoryTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryUseCaseTest {

    @Mock
    private CategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    private Category defaultCategory;

    @BeforeEach
    public void setUp() {
        defaultCategory = CategoryTestFactory.createDefaultCategory();
    }

    @Test
    void givenCategoryDoesNotExist_whenSaveCategoryIsCalled_thenCategoryIsSaved() {
        when(categoryPersistencePort.checkIfCategoryExists(defaultCategory.getName())).thenReturn(false);

        categoryUseCase.saveCategory(defaultCategory);

        verify(categoryPersistencePort).checkIfCategoryExists(defaultCategory.getName());
        verify(categoryPersistencePort).saveCategory(defaultCategory);

    }

    @Test
    void givenCategoryAlreadyExists_whenSaveCategoryIsCalled_thenThrowsException() {

        when(categoryPersistencePort.checkIfCategoryExists(defaultCategory.getName())).thenReturn(true);

        assertThrows(CategoryAlreadyExistsException.class, () -> categoryUseCase.saveCategory(defaultCategory));

        verify(categoryPersistencePort).checkIfCategoryExists(defaultCategory.getName());
        verify(categoryPersistencePort, never()).saveCategory(defaultCategory);

    }

}
