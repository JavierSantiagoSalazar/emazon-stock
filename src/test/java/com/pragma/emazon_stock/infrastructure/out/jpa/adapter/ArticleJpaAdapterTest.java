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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    void givenArticleNameAlreadyExists_whenCheckIfArticleExists_thenReturnTrue() {

        when(articleRepository.findByArticleName(defaultArticle.getArticleName())).thenReturn(Optional.of(defaultArticleEntity));

        Boolean result = articleJpaAdapter.checkIfArticleExists(defaultArticle.getArticleName());

        verify(articleRepository, times(1)).findByArticleName(defaultArticle.getArticleName());

        assertTrue(result);

    }

    @Test
    void givenArticleNameDoesNotExist_whenCheckIfArticleExists_thenReturnFalse() {

        when(articleRepository.findByArticleName(defaultArticle.getArticleName())).thenReturn(Optional.empty());

        Boolean result = articleJpaAdapter.checkIfArticleExists(defaultArticle.getArticleName());

        verify(articleRepository, times(1)).findByArticleName(defaultArticle.getArticleName());

        assertFalse(result);

    }

}
