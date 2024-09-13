package com.pragma.emazon_stock.domain.usecase;

import com.pragma.emazon_stock.domain.api.CategoryServicePort;
import com.pragma.emazon_stock.domain.exceptions.CategoryAlreadyExistsException;
import com.pragma.emazon_stock.domain.exceptions.NoContentCategoryException;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.model.Pagination;
import com.pragma.emazon_stock.domain.spi.CategoryPersistencePort;
import com.pragma.emazon_stock.domain.utils.PaginationUtils;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;

import static com.pragma.emazon_stock.domain.utils.Constants.DESC_COMPARATOR;

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

    @Override
    public Pagination<Category> getCategories(String sortOrder, Integer page, Integer size) {

        List<Category> categoryList = categoryPersistencePort.getAllCategories();

        if (categoryList.isEmpty()) {
            throw new NoContentCategoryException();
        }

        categoryList.sort(DESC_COMPARATOR.equalsIgnoreCase(sortOrder) ?
                Comparator.comparing(Category::getName).reversed() :
                Comparator.comparing(Category::getName));

        return PaginationUtils.paginate(categoryList, page, size);
    }

}
