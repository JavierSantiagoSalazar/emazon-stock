package com.pragma.emazon_stock.infrastructure.out.jpa.adapter;

import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.spi.CategoryPersistencePort;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.CategoryEntity;
import com.pragma.emazon_stock.infrastructure.out.jpa.mapper.CategoryEntityMapper;
import com.pragma.emazon_stock.infrastructure.out.jpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements CategoryPersistencePort {

    private final CategoryRepository categoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    @Override
    public void saveCategory(Category category) {
        categoryRepository.save(categoryEntityMapper.toEntity(category));
    }

    @Override
    public Boolean checkIfCategoryExists(String categoryName) {
        return categoryRepository.findByName(categoryName).isPresent();
    }

    @Override
    public List<Category> getAllCategories() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        return categoryEntityMapper.toCategoryList(categoryEntityList);
    }

}
