package com.pragma.emazon_stock.domain.usecase;

import com.pragma.emazon_stock.domain.api.CategoryServicePort;
import com.pragma.emazon_stock.domain.exceptions.CategoryAlreadyExistsException;
import com.pragma.emazon_stock.domain.exceptions.NoContentCategoryException;
import com.pragma.emazon_stock.domain.exceptions.PageOutOfBoundsException;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.model.Pagination;
import com.pragma.emazon_stock.domain.spi.CategoryPersistencePort;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;

import static com.pragma.emazon_stock.infrastructure.utils.Constants.DESC_COMPARATOR;

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

        if (DESC_COMPARATOR.equalsIgnoreCase(sortOrder)) {
            categoryList.sort(Comparator.comparing(Category::getName).reversed());
        } else {
            categoryList.sort(Comparator.comparing(Category::getName));
        }

        if (categoryList.isEmpty()) {
            throw new NoContentCategoryException();
        }

        Integer totalItems = categoryList.size();
        Integer totalPages = (int) Math.ceil((double) totalItems / size);
        Integer fromIndex = Math.min((page - 1) * size, totalItems);
        Integer toIndex = Math.min(fromIndex + size, totalItems);
        Boolean isLastPage = page >= totalPages;

        if (page > totalPages || page < 1) {
            throw new PageOutOfBoundsException(page, totalPages);
        }

        List<Category> paginatedCategories = categoryList.subList(fromIndex, toIndex);

        return new Pagination<>(
                paginatedCategories,
                page,
                size,
                (long) totalItems,
                totalPages,
                isLastPage
        );
    }

}
