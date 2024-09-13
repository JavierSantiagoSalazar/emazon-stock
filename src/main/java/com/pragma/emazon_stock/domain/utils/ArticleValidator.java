package com.pragma.emazon_stock.domain.utils;

import com.pragma.emazon_stock.domain.exceptions.ArticleCategoryOutOfBoundsException;
import com.pragma.emazon_stock.domain.exceptions.InvalidFilteringParameterException;
import com.pragma.emazon_stock.domain.exceptions.NoContentCategoryException;
import com.pragma.emazon_stock.domain.exceptions.NotUniqueArticleCategoriesException;
import com.pragma.emazon_stock.domain.model.Article;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.pragma.emazon_stock.domain.utils.Constants.GET_BY_ARTICLE;
import static com.pragma.emazon_stock.domain.utils.Constants.GET_BY_BRAND;
import static com.pragma.emazon_stock.domain.utils.Constants.GET_BY_CATEGORY;

public class ArticleValidator {

    public static final Short MAX_CATEGORIES = 3;

    private ArticleValidator() {
        throw new UnsupportedOperationException(Constants.UTILITY_CLASS);
    }

    public static void validateCategoryNames(List<String> categoryNames) {
        Set<String> uniqueCategoryNames = new HashSet<>(categoryNames);

        if (uniqueCategoryNames.size() != categoryNames.size()) {
            throw new NotUniqueArticleCategoriesException();
        }

        if (categoryNames.isEmpty() || categoryNames.size() > MAX_CATEGORIES) {
            throw new ArticleCategoryOutOfBoundsException();
        }
    }

    public static void validateData(String getBy, List<Article> articleList) {
        if (articleList.isEmpty()) {
            throw new NoContentCategoryException();
        }

        if (!getBy.equalsIgnoreCase(GET_BY_ARTICLE) &&
                !getBy.equalsIgnoreCase(GET_BY_BRAND) &&
                !getBy.equalsIgnoreCase(GET_BY_CATEGORY)) {
            throw new InvalidFilteringParameterException(getBy);
        }
    }

}
