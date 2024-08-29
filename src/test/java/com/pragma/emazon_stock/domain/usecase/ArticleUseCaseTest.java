package com.pragma.emazon_stock.domain.usecase;

import com.pragma.emazon_stock.domain.exceptions.ArticleAlreadyExistsException;
import com.pragma.emazon_stock.domain.exceptions.ArticleCategoryOutOfBoundsException;
import com.pragma.emazon_stock.domain.exceptions.CategoryDoesNotExistException;
import com.pragma.emazon_stock.domain.exceptions.NotUniqueArticleCategoriesException;
import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.spi.ArticlePersistencePort;
import com.pragma.emazon_stock.domain.spi.CategoryPersistencePort;
import com.pragma.emazon_stock.utils.ModelsTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ArticleUseCaseTest {

    @Mock
    private ArticlePersistencePort articlePersistencePort;
    @Mock
    private CategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private ArticleUseCase articleUseCase;

    private Article defaultArticle;

    @BeforeEach
    public void setUp() {
        defaultArticle = ModelsTestFactory.createDefaultArticle();
    }

    @Test
    void givenArticleDoesNotExist_whenSaveArticleIsCalled_thenArticleIsSaved() {

        when(articlePersistencePort.checkIfArticleExists(defaultArticle.getArticleName())).thenReturn(false);
        when(categoryPersistencePort.getAllCategoriesByName(anyList())).thenReturn(defaultArticle.getArticleCategories());

        articleUseCase.saveArticle(defaultArticle);

        verify(articlePersistencePort, times(1)).checkIfArticleExists(defaultArticle.getArticleName());
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
        verify(categoryPersistencePort, never()).getAllCategoriesByName(anyList());
        verify(articlePersistencePort, never()).saveArticle(defaultArticle);
    }

    @Test
    void givenNonExistentCategories_whenSaveArticleIsCalled_thenThrowsException() {

        when(articlePersistencePort.checkIfArticleExists(defaultArticle.getArticleName())).thenReturn(false);
        when(categoryPersistencePort.getAllCategoriesByName(anyList())).thenThrow(new CategoryDoesNotExistException(List.of("CATEGORY3")));

        assertThrows(CategoryDoesNotExistException.class, () -> articleUseCase.saveArticle(defaultArticle));

        verify(articlePersistencePort, times(1)).checkIfArticleExists(defaultArticle.getArticleName());
        verify(categoryPersistencePort, times(1)).getAllCategoriesByName(anyList());
        verify(articlePersistencePort, never()).saveArticle(defaultArticle);
    }

}
