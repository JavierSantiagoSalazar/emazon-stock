package com.pragma.emazon_stock.infrastructure.out.jpa.adapter;

import com.pragma.emazon_stock.domain.exceptions.CategoryDoesNotExistException;
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

    @Test
    void givenCategoriesExist_whenGetAllCategoriesByName_thenReturnCategoryList() {

        List<String> categoriesName = List.of("HOME", "ELECTRONICS");
        List<CategoryEntity> categoryEntities = List.of(
                new CategoryEntity(1, "HOME", "Home Description"),
                new CategoryEntity(2, "ELECTRONICS", "Electronics Description")
        );

        List<Category> categories = List.of(
                new Category(1, "HOME", "Home Description"),
                new Category(2, "ELECTRONICS", "Electronics Description")
        );

        when(categoryRepository.findByNameIn(categoriesName)).thenReturn(categoryEntities);
        when(categoryEntityMapper.toDomain(categoryEntities.get(0))).thenReturn(categories.get(0));
        when(categoryEntityMapper.toDomain(categoryEntities.get(1))).thenReturn(categories.get(1));

        List<Category> result = categoryJpaAdapter.getAllCategoriesByName(categoriesName);

        assertEquals(2, result.size());
        assertEquals("HOME", result.get(0).getName());
        assertEquals("ELECTRONICS", result.get(1).getName());

        verify(categoryRepository, times(1)).findByNameIn(categoriesName);
        verify(categoryEntityMapper, times(1)).toDomain(categoryEntities.get(0));
        verify(categoryEntityMapper, times(1)).toDomain(categoryEntities.get(1));
    }

    @Test
    void givenSomeCategoriesDoNotExist_whenGetAllCategoriesByName_thenThrowCategoryDoesNotExistException() {
        List<String> categoryNames = List.of("CATEGORY1", "CATEGORY2", "CATEGORY3");
        List<CategoryEntity> categoryEntities = List.of(
                new CategoryEntity(1, "CATEGORY1", "Description1"),
                new CategoryEntity(2, "CATEGORY2", "Description2")
        );

        when(categoryRepository.findByNameIn(categoryNames)).thenReturn(categoryEntities);

        CategoryDoesNotExistException thrownException = assertThrows(
                CategoryDoesNotExistException.class,
                () -> categoryJpaAdapter.getAllCategoriesByName(categoryNames)
        );

        assertTrue(thrownException.getMessage().contains("CATEGORY3"));

        verify(categoryRepository, times(1)).findByNameIn(categoryNames);
        verify(categoryEntityMapper, times(0)).toDomain(any(CategoryEntity.class));
    }

}