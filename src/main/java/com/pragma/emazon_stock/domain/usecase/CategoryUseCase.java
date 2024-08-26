package com.pragma.emazon_stock.domain.usecase;

import com.pragma.emazon_stock.domain.api.CategoryServicePort;
import com.pragma.emazon_stock.domain.exceptions.CategoryAlreadyExistsException;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.spi.CategoryPersistencePort;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CategoryUseCase implements CategoryServicePort {

    private final CategoryPersistencePort categoryPersistencePort;

    @Override
    public void saveCategory(Category category) {
        if (checkIfCategoryExists(category.getName())) {
            throw new CategoryAlreadyExistsException();
        }
        categoryPersistencePort.saveCategory(category);
    }

    @Override
    public boolean checkIfCategoryExists(String categoryName) {
        return categoryPersistencePort.checkIfCategoryExists(categoryName);
    }

}
