package com.pragma.emazon_stock.infrastructure.out.jpa.adapter;

import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.ArticleEntity;
import com.pragma.emazon_stock.infrastructure.out.jpa.mapper.ArticleEntityMapper;
import com.pragma.emazon_stock.infrastructure.out.jpa.repository.ArticleRepository;
import com.pragma.emazon_stock.utils.ModelsTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ArticleJpaAdapterTest {

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private ArticleEntityMapper articleEntityMapper;

    @InjectMocks
    private ArticleJpaAdapter articleJpaAdapter;

    private Article defaultArticle;
    private ArticleEntity defaultArticleEntity;

    @BeforeEach
    public void setUp() {

        defaultArticle = ModelsTestFactory.createDefaultArticle();
        defaultArticleEntity = ModelsTestFactory.createDefaultArticleEntity();
    }

    @Test
    void givenArticle_whenSaveArticleIsCalled_thenArticleIsSaved() {

        when(articleEntityMapper.toEntity(defaultArticle)).thenReturn(defaultArticleEntity);

        articleJpaAdapter.saveArticle(defaultArticle);

        verify(articleRepository, times(1)).save(defaultArticleEntity);
        verify(articleEntityMapper, times(1)).toEntity(defaultArticle);
    }

    @Test
    void givenArticleNameAlreadyExists_whenGetArticleById_thenReturnTrue() {

        when(articleRepository.findByArticleName(defaultArticle.getArticleName())).thenReturn(Optional.of(defaultArticleEntity));

        Boolean result = articleJpaAdapter.checkIfArticleExists(defaultArticle.getArticleName());

        verify(articleRepository, times(1)).findByArticleName(defaultArticle.getArticleName());

        assertTrue(result);
    }

    @Test
    void givenArticleNameDoesNotExist_whenGetArticleById_thenReturnFalse() {

        when(articleRepository.findByArticleName(defaultArticle.getArticleName())).thenReturn(Optional.empty());

        Boolean result = articleJpaAdapter.checkIfArticleExists(defaultArticle.getArticleName());

        verify(articleRepository, times(1)).findByArticleName(defaultArticle.getArticleName());

        assertFalse(result);
    }

    @Test
    void givenArticlesExist_whenGetAllArticles_thenReturnArticlesList() {

        ArticleEntity articleEntity = new ArticleEntity(
                1,
                "Laptop",
                "A powerful laptop",
                5,
                1500.0,
                null,
                null
        );
        List<ArticleEntity> articleEntityList = List.of(articleEntity);

        Article article = new Article(
                1,
                "Laptop",
                "A powerful laptop",
                5,
                1500.0,
                null,
                null
        );
        List<Article> articleList = List.of(article);

        when(articleRepository.findAll()).thenReturn(articleEntityList);
        when(articleEntityMapper.toArticleList(articleEntityList)).thenReturn(articleList);

        List<Article> result = articleJpaAdapter.getAllArticles();

        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getArticleName());
        assertEquals("A powerful laptop", result.get(0).getArticleDescription());

        verify(articleRepository, times(1)).findAll();
        verify(articleEntityMapper, times(1)).toArticleList(articleEntityList);
    }

    @Test
    void givenArticleIds_whenGetArticlesByIds_thenReturnArticleList() {

        List<Integer> articleIds = List.of(1, 2);
        List<ArticleEntity> articleEntityList = List.of(defaultArticleEntity);
        List<Article> articleList = List.of(defaultArticle);

        when(articleRepository.findAllByArticleId(articleIds)).thenReturn(articleEntityList);
        when(articleEntityMapper.toArticleList(articleEntityList)).thenReturn(articleList);

        List<Article> result = articleJpaAdapter.getArticlesByIds(articleIds);

        assertEquals(1, result.size());
        assertEquals(defaultArticle.getArticleName(), result.get(0).getArticleName());

        verify(articleRepository, times(1)).findAllByArticleId(articleIds);
        verify(articleEntityMapper, times(1)).toArticleList(articleEntityList);
    }

    @Test
    void givenArticles_whenSaveAllArticles_thenReturnTrue() {

        List<Article> articleList = List.of(defaultArticle);
        List<ArticleEntity> articleEntityList = List.of(defaultArticleEntity);

        when(articleEntityMapper.toEntityList(articleList)).thenReturn(articleEntityList);
        when(articleRepository.saveAll(articleEntityList)).thenReturn(articleEntityList);

        Boolean result = articleJpaAdapter.saveAllArticles(articleList);

        assertTrue(result);

        verify(articleRepository, times(1)).saveAll(articleEntityList);
        verify(articleEntityMapper, times(1)).toEntityList(articleList);
    }

    @Test
    void givenEmptyArticleList_whenSaveAllArticles_thenReturnFalse() {

        List<Article> articleList = List.of(defaultArticle);
        List<ArticleEntity> articleEntityList = List.of(defaultArticleEntity);

        when(articleEntityMapper.toEntityList(articleList)).thenReturn(articleEntityList);
        when(articleRepository.saveAll(articleEntityList)).thenReturn(Collections.emptyList());

        Boolean result = articleJpaAdapter.saveAllArticles(articleList);

        assertFalse(result);

        verify(articleRepository, times(1)).saveAll(articleEntityList);
        verify(articleEntityMapper, times(1)).toEntityList(articleList);
    }

}
