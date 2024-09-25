package com.pragma.emazon_stock.application.handler;

import com.pragma.emazon_stock.application.dto.article.ArticleRequest;
import com.pragma.emazon_stock.application.dto.article.ArticleResponse;
import com.pragma.emazon_stock.application.dto.article.SupplyRequest;
import com.pragma.emazon_stock.application.dto.brand.BrandResponse;
import com.pragma.emazon_stock.application.dto.category.EmbeddedCategoryResponse;
import com.pragma.emazon_stock.application.handler.article.ArticleHandlerImpl;
import com.pragma.emazon_stock.application.mappers.article.ArticleRequestMapper;
import com.pragma.emazon_stock.application.mappers.article.ArticleResponseMapper;
import com.pragma.emazon_stock.application.mappers.article.SupplyRequestMapper;
import com.pragma.emazon_stock.domain.api.ArticleServicePort;
import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.domain.model.Brand;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.model.Pagination;
import com.pragma.emazon_stock.domain.model.Supply;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ArticleHandlerImplTest {

    @Mock
    private ArticleServicePort articleServicePort;

    @Mock
    private ArticleRequestMapper articleRequestMapper;

    @Mock
    private ArticleResponseMapper articleResponseMapper;

    @Mock
    private SupplyRequestMapper supplyRequestMapper;

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

    @Test
    void givenValidParameters_whenGetArticles_thenReturnsArticleResponses() {

        String sortOrder = "asc";
        String filterBy = "articleName";
        String brandName = "HP";
        String categoryName = "COMPUTERS";
        Integer page = 1;
        Integer size = 10;

        Article article = new Article(
                1,
                "Laptop",
                "A powerful laptop",
                5,
                1500.0,
                new Brand(1, "HP", "HP Brand", new ArrayList<>()),
                List.of(new Category(1, "Electronics", "Electronics category"))
        );

        Pagination<Article> paginationArticles = new Pagination<>(
                List.of(article),
                page,
                size,
                1L,
                1,
                true
        );

        ArticleResponse articleResponse = getArticleResponse();

        when(articleServicePort.getArticles(sortOrder, filterBy, brandName, categoryName, page, size))
                .thenReturn(paginationArticles);
        when(articleResponseMapper.toResponse(article)).thenReturn(articleResponse);

        Pagination<ArticleResponse> result = articleHandlerImpl.getArticles(
                sortOrder,
                filterBy,
                brandName,
                categoryName,
                page,
                size
        );

        assertEquals(1, result.getItems().size());
        assertEquals("Laptop", result.getItems().get(0).getArticleName());
        assertEquals(page, result.getPageNo());
        assertEquals(size, result.getPageSize());
        assertEquals(1, result.getTotalPages());
        assertTrue(result.getIsLastPage());

        verify(articleServicePort, times(1))
                .getArticles(sortOrder, filterBy, brandName, categoryName, page, size);
        verify(articleResponseMapper, times(1)).toResponse(article);
    }

    private static ArticleResponse getArticleResponse() {
        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setBrandId(1);
        brandResponse.setBrandName("HP");
        brandResponse.setBrandDescription("HP Brand");
        EmbeddedCategoryResponse embeddedCategoryResponse = new EmbeddedCategoryResponse(1, "COMPUTERS");

        ArticleResponse articleResponse = new ArticleResponse();
        articleResponse.setArticleId(1);
        articleResponse.setArticleName("Laptop");
        articleResponse.setArticleDescription("A powerful laptop");
        articleResponse.setArticleAmount(5);
        articleResponse.setArticlePrice(1500.0);
        articleResponse.setArticleBrand(brandResponse);
        articleResponse.setArticleCategories(List.of(embeddedCategoryResponse));
        return articleResponse;
    }

    @Test
    void givenSupplyRequest_whenUpdateArticleSupplyIsCalled_thenReturnTrue() {

        SupplyRequest supplyRequest = new SupplyRequest();
        supplyRequest.setSupplyArticleIds(List.of(10, 5));
        supplyRequest.setSupplyArticleAmounts(List.of(100, 600));
        Supply supply = new Supply(List.of(10, 5), List.of(100, 600));

        when(supplyRequestMapper.toDomain(supplyRequest)).thenReturn(supply);
        when(articleServicePort.updateArticleSupply(supply)).thenReturn(true);

        Boolean result = articleHandlerImpl.updateArticleSupply(supplyRequest);

        verify(supplyRequestMapper, times(1)).toDomain(supplyRequest);
        verify(articleServicePort, times(1)).updateArticleSupply(supply);

        assertTrue(result);
    }

    @Test
    void givenArticleIdList_whenGetArticlesByIds_thenReturnArticleResponses() {

        List<Integer> articleIdList = List.of(1, 2, 3);

        List<Article> articles = getArticles();

        ArticleResponse articleResponse1 = getArticleResponse();
        ArticleResponse articleResponse2 = getArticleResponse(articleResponse1);

        when(articleServicePort.getArticlesByIds(articleIdList)).thenReturn(articles);
        when(articleResponseMapper.toResponse(articles)).thenReturn(List.of(articleResponse1, articleResponse2));

        List<ArticleResponse> result = articleHandlerImpl.getArticlesByIds(articleIdList);

        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getArticleName());
        assertEquals("Printer", result.get(1).getArticleName());
        assertEquals(1, result.get(0).getArticleId());
        assertEquals(2, result.get(1).getArticleId());

        verify(articleServicePort, times(1)).getArticlesByIds(articleIdList);
        verify(articleResponseMapper, times(1)).toResponse(articles);
    }

    private static List<Article> getArticles() {
        Article article1 = new Article(
                1,
                "Laptop",
                "A powerful laptop",
                5,
                1500.0,
                new Brand(1, "HP", "HP Brand", new ArrayList<>()),
                List.of(new Category(1, "Electronics", "Electronics category"))
        );

        Article article2 = new Article(
                2,
                "Printer",
                "A high-quality printer",
                10,
                300.0,
                new Brand(1, "HP", "HP Brand", new ArrayList<>()),
                List.of(new Category(2, "Office Supplies", "Office category"))
        );


        return List.of(article1, article2);
    }

    private static ArticleResponse getArticleResponse(ArticleResponse articleResponse1) {
        ArticleResponse articleResponse2 = new ArticleResponse();
        articleResponse2.setArticleId(2);
        articleResponse2.setArticleName("Printer");
        articleResponse2.setArticleDescription("A high-quality printer");
        articleResponse2.setArticleAmount(10);
        articleResponse2.setArticlePrice(300.0);
        articleResponse2.setArticleBrand(articleResponse1.getArticleBrand());
        articleResponse2.setArticleCategories(List.of(new EmbeddedCategoryResponse(2, "OFFICE SUPPLIES")));
        return articleResponse2;
    }

}
