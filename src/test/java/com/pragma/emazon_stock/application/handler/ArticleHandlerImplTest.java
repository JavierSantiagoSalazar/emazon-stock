package com.pragma.emazon_stock.application.handler;

import com.pragma.emazon_stock.application.dto.article.ArticleRequest;
import com.pragma.emazon_stock.application.handler.article.ArticleHandlerImpl;
import com.pragma.emazon_stock.application.mappers.article.ArticleRequestMapper;
import com.pragma.emazon_stock.domain.api.ArticleServicePort;
import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.model.Category;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@SpringBootTest
class ArticleHandlerImplTest {

    @Mock
    private ArticleServicePort articleServicePort;

    @Mock
    private ArticleRequestMapper articleRequestMapper;

    @InjectMocks
    private ArticleHandlerImpl articleHandlerImpl;

    @Test
    void givenValidArticleRequest_whenCreateArticle_thenArticleIsSaved() {

        ArticleRequest articleRequest = new ArticleRequest();
        articleRequest.setArticleName("  New Article  ");
        articleRequest.setArticleDescription("  A great article  ");
        articleRequest.setArticleAmount(10);
        articleRequest.setArticlePrice(100.0);
        articleRequest.setArticleCategories(Arrays.asList("  Category1  ", "  Category2  "));
        articleRequest.setArticleBrand("HP");

        Article mappedArticle = new Article(
                null,
                "NEW ARTICLE",
                "A great article",
                10,
                100.0,
                new Brand(1, "HP", "HP items", new ArrayList<>()),
                Arrays.asList(
                        new Category(null, "CATEGORY1", null),
                        new Category(null,"CATEGORY2", null)
                )
        );

        when(articleRequestMapper.toDomain(articleRequest)).thenReturn(mappedArticle);

        articleHandlerImpl.createArticle(articleRequest);

        verify(articleRequestMapper, times(1)).toDomain(articleRequest);
        verify(articleServicePort, times(1)).saveArticle(mappedArticle);
    }

}
