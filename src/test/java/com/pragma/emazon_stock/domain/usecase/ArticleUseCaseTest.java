package com.pragma.emazon_stock.domain.usecase;

import com.pragma.emazon_stock.domain.exceptions.*;
import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.model.Pagination;
import com.pragma.emazon_stock.domain.spi.ArticlePersistencePort;
import com.pragma.emazon_stock.domain.spi.BrandPersistencePort;
import com.pragma.emazon_stock.domain.spi.CategoryPersistencePort;
import com.pragma.emazon_stock.utils.ModelsTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


@SpringBootTest
class ArticleUseCaseTest {

    @Mock
    private ArticlePersistencePort articlePersistencePort;
    @Mock
    private CategoryPersistencePort categoryPersistencePort;
    @Mock
    private BrandPersistencePort brandPersistencePort;

    @InjectMocks
    private ArticleUseCase articleUseCase;

    private Article defaultArticle;
    private List<Article> articleList;


    @BeforeEach
    public void setUp() {

        defaultArticle = ModelsTestFactory.createDefaultArticle();
        articleList = List.of(defaultArticle);
    }

    @Test
    void givenArticleDoesNotExist_whenSaveArticleIsCalled_thenArticleIsSaved() {

        when(articlePersistencePort.checkIfArticleExists(defaultArticle.getArticleName())).thenReturn(false);
        when(brandPersistencePort.getBrandByName(defaultArticle.getArticleBrand().getBrandName())).thenReturn(defaultArticle.getArticleBrand());
        when(categoryPersistencePort.getAllCategoriesByName(anyList())).thenReturn(defaultArticle.getArticleCategories());

        articleUseCase.saveArticle(defaultArticle);

        verify(articlePersistencePort, times(1)).checkIfArticleExists(defaultArticle.getArticleName());
        verify(brandPersistencePort, times(1)).getBrandByName(defaultArticle.getArticleBrand().getBrandName());
        verify(categoryPersistencePort, times(1)).getAllCategoriesByName(anyList());
        verify(articlePersistencePort, times(1)).saveArticle(defaultArticle);

    }

    @Test
    void givenArticleAlreadyExists_whenSaveArticleIsCalled_thenThrowsException() {

        when(articlePersistencePort.checkIfArticleExists(defaultArticle.getArticleName())).thenReturn(true);

        assertThrows(ArticleAlreadyExistsException.class, () -> articleUseCase.saveArticle(defaultArticle));

        verify(articlePersistencePort, times(1)).checkIfArticleExists(defaultArticle.getArticleName());
        verify(categoryPersistencePort, never()).getAllCategoriesByName(anyList());
        verify(articlePersistencePort, never()).saveArticle(defaultArticle);
    }

    @Test
    void givenDuplicateCategoriesInArticle_whenSaveArticleIsCalled_thenThrowsException() {

        defaultArticle.setArticleCategories(Arrays.asList(
                new Category(null, "CATEGORY1", null),
                new Category(null, "CATEGORY1", null),
                new Category(null, "CATEGORY2", null)
        ));

        when(articlePersistencePort.checkIfArticleExists(defaultArticle.getArticleName())).thenReturn(false);

        assertThrows(NotUniqueArticleCategoriesException.class, () -> articleUseCase.saveArticle(defaultArticle));

        verify(articlePersistencePort, times(1)).checkIfArticleExists(defaultArticle.getArticleName());
        verify(brandPersistencePort, never()).getBrandByName(any());
        verify(categoryPersistencePort, never()).getAllCategoriesByName(anyList());
        verify(articlePersistencePort, never()).saveArticle(defaultArticle);
    }

    @Test
    void givenTooManyCategoriesInArticle_whenSaveArticleIsCalled_thenThrowsException() {

        defaultArticle.setArticleCategories(Arrays.asList(
                new Category(null, "CATEGORY1", null),
                new Category(null, "CATEGORY2", null),
                new Category(null, "CATEGORY3", null),
                new Category(null, "CATEGORY4", null)
        ));

        when(articlePersistencePort.checkIfArticleExists(defaultArticle.getArticleName())).thenReturn(false);

        assertThrows(ArticleCategoryOutOfBoundsException.class, () -> articleUseCase.saveArticle(defaultArticle));

        verify(articlePersistencePort, times(1)).checkIfArticleExists(defaultArticle.getArticleName());
        verify(brandPersistencePort, never()).getBrandByName(any());
        verify(categoryPersistencePort, never()).getAllCategoriesByName(anyList());
        verify(articlePersistencePort, never()).saveArticle(defaultArticle);
    }

    @Test
    void givenNonExistentCategories_whenSaveArticleIsCalled_thenThrowsException() {

        when(articlePersistencePort.checkIfArticleExists(defaultArticle.getArticleName())).thenReturn(false);
        when(categoryPersistencePort.getAllCategoriesByName(anyList())).thenThrow(
                new CategoryDoesNotExistException(List.of("CATEGORY3"))
        );

        assertThrows(CategoryDoesNotExistException.class, () -> articleUseCase.saveArticle(defaultArticle));

        verify(articlePersistencePort, times(1)).checkIfArticleExists(defaultArticle.getArticleName());
        verify(categoryPersistencePort, times(1)).getAllCategoriesByName(anyList());
        verify(articlePersistencePort, never()).saveArticle(defaultArticle);
    }

    @Test
    void givenNonExistentBrand_whenSaveArticleIsCalled_thenThrowsException() {

        when(articlePersistencePort.checkIfArticleExists(defaultArticle.getArticleName())).thenReturn(false);
        when(brandPersistencePort.getBrandByName(defaultArticle.getArticleBrand().getBrandName())).thenThrow(
                new BrandDoesNotExistException("HP")
        );
        when(categoryPersistencePort.getAllCategoriesByName(anyList())).thenReturn(defaultArticle.getArticleCategories());

        assertThrows(BrandDoesNotExistException.class, () -> articleUseCase.saveArticle(defaultArticle));

        verify(articlePersistencePort, times(1)).checkIfArticleExists(defaultArticle.getArticleName());
        verify(brandPersistencePort, times(1)).getBrandByName(defaultArticle.getArticleBrand().getBrandName());
        verify(categoryPersistencePort, never()).getAllCategoriesByName(anyList());
        verify(articlePersistencePort, never()).saveArticle(defaultArticle);
    }

    @Test
    void givenArticlesFilteredByCategory_whenGetArticles_thenReturnFilteredArticles() {

        String categoryName = defaultArticle.getArticleCategories().get(0).getName();
        when(articlePersistencePort.getAllArticles()).thenReturn(articleList);

        Pagination<Article> result = articleUseCase.getArticles(
                "asc",
                "categoryName",
                null,
                categoryName,
                1,
                10
        );

        assertEquals(1, result.getItems().size());
        verify(articlePersistencePort, times(1)).getAllArticles();
    }

    @Test
    void givenNoArticlesForCategory_whenGetArticles_thenThrowNoContentCategoryException() {

        when(articlePersistencePort.getAllArticles()).thenReturn(List.of());

        assertThrows(NoContentCategoryException.class, () ->
                articleUseCase.getArticles(
                        "asc",
                        "categoryName",
                        null,
                        "NonExistentCategory",
                        1,
                        10)
        );
    }

    @Test
    void givenArticlesFilteredByBrand_whenGetArticles_thenReturnFilteredArticles() {

        String brandName = defaultArticle.getArticleBrand().getBrandName();
        when(articlePersistencePort.getAllArticles()).thenReturn(articleList);

        Pagination<Article> result = articleUseCase.getArticles(
                "asc",
                "brandName",
                brandName,
                null,
                1,
                10
        );

        assertEquals(1, result.getItems().size());
        verify(articlePersistencePort, times(1)).getAllArticles();
    }

    @Test
    void givenArticlesSortedAsc_whenGetArticles_thenReturnSortedArticles() {

        Article anotherArticle = new Article(
                2,
                "ZBrand",
                "Description",
                10,
                100.0,
                defaultArticle.getArticleBrand(),
                defaultArticle.getArticleCategories()
        );

        List<Article> unsortedArticleList = new ArrayList<>(List.of(defaultArticle, anotherArticle));

        when(articlePersistencePort.getAllArticles()).thenReturn(unsortedArticleList);

        Pagination<Article> result = articleUseCase.getArticles(
                "asc",
                "articleName",
                null,
                null,
                1,
                10
        );

        assertEquals("PC", result.getItems().get(0).getArticleName());
        verify(articlePersistencePort, times(1)).getAllArticles();
    }

    @Test
    void givenInvalidFilterByParameter_whenGetArticles_thenThrowInvalidFilteringParameterException() {

        when(articlePersistencePort.getAllArticles()).thenReturn(articleList);

        assertThrows(InvalidFilteringParameterException.class, () ->
                articleUseCase.getArticles(
                        "asc",
                        "invalidFilter",
                        null,
                        null,
                        1,
                        10)
        );
    }

    @Test
    void givenOutOfBoundsPage_whenGetArticles_thenThrowPageOutOfBoundsException() {

        List<Article> mutableArticleList = new ArrayList<>(articleList);

        when(articlePersistencePort.getAllArticles()).thenReturn(mutableArticleList);

        assertThrows(PageOutOfBoundsException.class, () ->
                articleUseCase.getArticles(
                        "asc",
                        "articleName",
                        null,
                        null,
                        2,
                        10)
        );
    }

    @Test
    void givenValidPagination_whenGetArticles_thenReturnPaginatedArticles() {

        List<Article> mutableArticleList = new ArrayList<>(articleList);

        when(articlePersistencePort.getAllArticles()).thenReturn(mutableArticleList);

        Pagination<Article> result = articleUseCase.getArticles(
                "asc",
                "articleName",
                null,
                null,
                1, 1
        );

        assertEquals(1, result.getItems().size());
        assertEquals(1, result.getTotalPages());
        verify(articlePersistencePort, times(1)).getAllArticles();
    }

}
