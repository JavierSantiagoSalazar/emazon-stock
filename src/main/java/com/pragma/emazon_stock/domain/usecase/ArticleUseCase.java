package com.pragma.emazon_stock.domain.usecase;

import com.pragma.emazon_stock.domain.api.ArticleServicePort;
import com.pragma.emazon_stock.domain.exceptions.*;
import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.model.Pagination;
import com.pragma.emazon_stock.domain.spi.ArticlePersistencePort;
import com.pragma.emazon_stock.domain.spi.BrandPersistencePort;
import com.pragma.emazon_stock.domain.spi.CategoryPersistencePort;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

import static com.pragma.emazon_stock.infrastructure.utils.Constants.DESC_COMPARATOR;

@AllArgsConstructor
public class ArticleUseCase implements ArticleServicePort {

    public static final String GET_BY_ARTICLE = "articleName";
    public static final String GET_BY_BRAND = "brandName";
    public static final String GET_BY_CATEGORY = "categoryName";

    private final ArticlePersistencePort articlePersistencePort;
    private final CategoryPersistencePort categoryPersistencePort;
    private final BrandPersistencePort brandPersistencePort;

    @Override
    public void saveArticle(Article article) {

        if (checkIfArticleExists(article.getArticleName())) {
            throw new ArticleAlreadyExistsException();
        }

        List<String> categoryNames = article.getArticleCategories().stream()
                .map(Category::getName)
                .toList();

        validateCategoryNames(categoryNames);

        Brand brand = brandPersistencePort.getBrandByName(article.getArticleBrand().getBrandName());
        List<Category> categoryList = categoryPersistencePort.getAllCategoriesByName(categoryNames);

        article.setArticleBrand(brand);
        article.setArticleCategories(categoryList);
        articlePersistencePort.saveArticle(article);
    }

    @Override
    public boolean checkIfArticleExists(String articleName) {

        return articlePersistencePort.checkIfArticleExists(articleName);
    }

    @Override
    public Pagination<Article> getArticles(
            String sortOrder, String filterBy, String brandName,
            String categoryName, Integer page, Integer size
    ) {

        List<Article> articleList = articlePersistencePort.getAllArticles();

        validateData(filterBy, articleList);

        if (filterBy.equalsIgnoreCase(GET_BY_CATEGORY)) {
            articleList = articleList.stream()
                    .filter(article -> article.getArticleCategories().stream()
                            .anyMatch(category -> category.getName().equals(categoryName)))
                    .collect(Collectors.toList());
        }

        if (filterBy.equalsIgnoreCase(GET_BY_BRAND)) {
            articleList = articleList.stream()
                    .filter(article -> article.getArticleBrand().getBrandName().equals(brandName))
                    .collect(Collectors.toList());
        }

        articleList.sort(getComparatorForSorting(sortOrder));

        Integer totalItems = articleList.size();
        Integer totalPages = (int) Math.ceil((double) totalItems / size);
        Integer fromIndex = Math.min((page - 1) * size, totalItems);
        Integer toIndex = Math.min(fromIndex + size, totalItems);
        Boolean isLastPage = page >= totalPages;

        if (page > totalPages || page < 1) {
            throw new PageOutOfBoundsException(page, totalPages);
        }

        List<Article> paginatedArticles = articleList.subList(fromIndex, toIndex);

        if (paginatedArticles.isEmpty()) {
            throw new NoContentArticleException();
        }

        return new Pagination<>(
                paginatedArticles,
                page,
                size,
                (long) totalItems,
                totalPages,
                isLastPage
        );
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

    private Comparator<Article> getComparatorForSorting(String sortOrder) {
        return DESC_COMPARATOR.equalsIgnoreCase(sortOrder) ?
                Comparator.comparing(Article::getArticleName).reversed() :
                Comparator.comparing(Article::getArticleName);
    }

    private void validateData(String getBy, List<Article> articleList) {

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
