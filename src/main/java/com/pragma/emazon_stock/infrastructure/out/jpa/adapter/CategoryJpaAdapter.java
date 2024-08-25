package com.pragma.emazon_stock.infrastructure.out.jpa.adapter;

import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.spi.CategoryPersistencePort;
import com.pragma.emazon_stock.infrastructure.configuration.exception.exceptions.CategoryAlreadyExistsException;
import com.pragma.emazon_stock.infrastructure.out.jpa.mapper.CategoryEntityMapper;
import com.pragma.emazon_stock.infrastructure.out.jpa.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

    @RequiredArgsConstructor
    public class CategoryJpaAdapter implements CategoryPersistencePort {

        private final CategoryRepository categoryRepository;
        private final CategoryEntityMapper categoryEntityMapper;

        @Override
        public void saveCategory(Category category) {
            if (categoryRepository.findByName(category.getName()).isPresent()) {
                throw new CategoryAlreadyExistsException();
            }
            categoryRepository.save(categoryEntityMapper.domainCategoryToCategoryEntity(category));
        }

    }
