package com.pragma.emazon_stock.infrastructure.out.jpa.adapter;

import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.emazon_stock.infrastructure.out.jpa.mapper.CategoryEntityMapper;
import com.pragma.emazon_stock.infrastructure.out.jpa.repository.CategoryRepository;
import com.pragma.emazon_stock.utils.CategoryTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryJpaAdapterTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryEntityMapper categoryEntityMapper;

    @InjectMocks
    private CategoryJpaAdapter categoryJpaAdapter;

    private Category defaultCategory;
    private CategoryEntity defaultCategoryEntity;

    @BeforeEach
    public void setUp() {
        defaultCategory = CategoryTestFactory.createDefaultCategory();
        defaultCategoryEntity = CategoryTestFactory.createDefaultCategoryEntity();
    }

    @Test
    void givenCategory_whenSaveCategoryIsCalled_thenCategoryIsSaved() {

        when(categoryEntityMapper.domainCategoryToCategoryEntity(defaultCategory)).thenReturn(defaultCategoryEntity);

        categoryJpaAdapter.saveCategory(defaultCategory);

        verify(categoryRepository).save(defaultCategoryEntity);
        verify(categoryEntityMapper).domainCategoryToCategoryEntity(defaultCategory);

    }

    @Test
    void givenCategoryNameAlreadyExists_whenCheckIfCategoryExists_thenReturnTrue() {

        when(categoryRepository.findByName(defaultCategory.getName())).thenReturn(Optional.of(defaultCategoryEntity));

        Boolean result = categoryJpaAdapter.checkIfCategoryExists(defaultCategory.getName());

        verify(categoryRepository).findByName(defaultCategory.getName());

        assertTrue(result);
    }

    @Test
    void givenCategoryNameDoesNotExist_whenCheckIfCategoryExists_thenReturnFalse() {

        when(categoryRepository.findByName(defaultCategory.getName())).thenReturn(Optional.empty());

        Boolean result = categoryJpaAdapter.checkIfCategoryExists(defaultCategory.getName());

        verify(categoryRepository).findByName(defaultCategory.getName());

        assertFalse(result);
    }

}