package com.pragma.emazon_stock.domain.spi;

import com.pragma.emazon_stock.domain.model.Category;

public interface CategoryPersistencePort {

    void saveCategory(Category category);

    Boolean checkIfCategoryExists(String categoryName);

}
