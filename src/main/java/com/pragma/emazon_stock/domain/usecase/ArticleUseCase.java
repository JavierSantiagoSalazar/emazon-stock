package com.pragma.emazon_stock.domain.usecase;

import com.pragma.emazon_stock.domain.api.ArticleServicePort;
import com.pragma.emazon_stock.domain.exceptions.ArticleAlreadyExistsException;
import com.pragma.emazon_stock.domain.exceptions.ArticleCategoryOutOfBoundsException;
import com.pragma.emazon_stock.domain.exceptions.NotUniqueArticleCategoriesException;
import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.spi.ArticlePersistencePort;
import com.pragma.emazon_stock.domain.spi.CategoryPersistencePort;
import lombok.AllArgsConstructor;

import java.util.*;

@AllArgsConstructor
public class ArticleUseCase implements ArticleServicePort {

    private final ArticlePersistencePort articlePersistencePort;
    private final CategoryPersistencePort categoryPersistencePort;

    @Override
    public void saveArticle(Article article) {

        if (checkIfArticleExists(article.getArticleName())) {
            throw new ArticleAlreadyExistsException();
        }

        List<String> categoryNames = article.getArticleCategories().stream()
                .map(Category::getName)
                .toList();

        validateCategoryNames(categoryNames);

        List<Category> categoryList = categoryPersistencePort.getAllCategoriesByName(categoryNames);

        article.setArticleCategories(categoryList);
        articlePersistencePort.saveArticle(article);
    }

    @Override
    public boolean checkIfArticleExists(String articleName) {

        return articlePersistencePort.checkIfArticleExists(articleName);
    }

    private void validateCategoryNames(List<String> categoryNames) {

        Set<String> uniqueCategoryNames = new HashSet<>(categoryNames);

        if (uniqueCategoryNames.size() != categoryNames.size()) {
            throw new NotUniqueArticleCategoriesException();
        }

        if (categoryNames.isEmpty() || categoryNames.size() > 3) {
            throw new ArticleCategoryOutOfBoundsException();
        }
    }

}
