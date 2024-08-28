package com.pragma.emazon_stock.infrastructure.out.jpa.adapter;

import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.emazon_stock.infrastructure.out.jpa.mapper.CategoryEntityMapper;
import com.pragma.emazon_stock.infrastructure.out.jpa.repository.CategoryRepository;
import com.pragma.emazon_stock.utils.ModelsTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        defaultCategory = ModelsTestFactory.createDefaultCategory();
        defaultCategoryEntity = ModelsTestFactory.createDefaultCategoryEntity();
    }

    @Test
    void givenCategory_whenSaveCategoryIsCalled_thenCategoryIsSaved() {

        when(categoryEntityMapper.toEntity(defaultCategory)).thenReturn(defaultCategoryEntity);

        categoryJpaAdapter.saveCategory(defaultCategory);

        verify(categoryRepository, times(1)).save(defaultCategoryEntity);
        verify(categoryEntityMapper, times(1)).toEntity(defaultCategory);

    }

    @Test
    void givenCategoryNameAlreadyExists_whenCheckIfCategoryExists_thenReturnTrue() {

        when(categoryRepository.findByName(defaultCategory.getName())).thenReturn(Optional.of(defaultCategoryEntity));

        Boolean result = categoryJpaAdapter.checkIfCategoryExists(defaultCategory.getName());

        verify(categoryRepository, times(1)).findByName(defaultCategory.getName());

        assertTrue(result);
    }

    @Test
    void givenCategoryNameDoesNotExist_whenCheckIfCategoryExists_thenReturnFalse() {

        when(categoryRepository.findByName(defaultCategory.getName())).thenReturn(Optional.empty());

        Boolean result = categoryJpaAdapter.checkIfCategoryExists(defaultCategory.getName());

        verify(categoryRepository, times(1)).findByName(defaultCategory.getName());

        assertFalse(result);

    }

    @Test
    void whenGetAllCategories_thenReturnCategoriesList() {

        CategoryEntity categoryEntity = new CategoryEntity(1, "HOME", "Description");
        List<CategoryEntity> categoryEntityList = List.of(categoryEntity);

        Category category = new Category(1, "HOME", "Description");
        List<Category> categoryList = List.of(category);

        when(categoryRepository.findAll()).thenReturn(categoryEntityList);
        when(categoryEntityMapper.toCategoryList(categoryEntityList)).thenReturn(categoryList);

        List<Category> result = categoryJpaAdapter.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("HOME", result.get(0).getName());
        assertEquals("Description", result.get(0).getDescription());

        verify(categoryRepository, times(1)).findAll();

        verify(categoryEntityMapper, times(1)).toCategoryList(categoryEntityList);
    }

}