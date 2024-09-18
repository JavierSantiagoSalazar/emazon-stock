package com.pragma.emazon_stock.application.handler.article;

import com.pragma.emazon_stock.application.dto.article.ArticleRequest;
import com.pragma.emazon_stock.application.dto.article.ArticleResponse;
import com.pragma.emazon_stock.application.dto.article.SupplyRequest;
import com.pragma.emazon_stock.application.mappers.article.ArticleRequestMapper;
import com.pragma.emazon_stock.application.mappers.article.ArticleResponseMapper;
import com.pragma.emazon_stock.application.mappers.article.SupplyRequestMapper;
import com.pragma.emazon_stock.domain.api.ArticleServicePort;
import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.domain.model.Pagination;
import com.pragma.emazon_stock.domain.model.Supply;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ArticleHandlerImpl implements ArticleHandler {

    private final ArticleServicePort articleServicePort;
    private final ArticleRequestMapper articleRequestMapper;
    private final ArticleResponseMapper articleResponseMapper;
    private final SupplyRequestMapper supplyRequestMapper;

    @Override
    public void createArticle(ArticleRequest articleRequest) {

        List<String> normalizedCategories = articleRequest.getArticleCategories().stream()
                .map(category -> category.trim().toUpperCase())
                .toList();

        articleRequest.setArticleName(articleRequest.getArticleName().trim().toUpperCase());
        articleRequest.setArticleDescription(articleRequest.getArticleDescription().trim());
        articleRequest.setArticleBrand(articleRequest.getArticleBrand().trim().toUpperCase());
        articleRequest.setArticleCategories(normalizedCategories);

        articleServicePort.saveArticle(articleRequestMapper.toDomain(articleRequest));
    }

    @Override
    public Pagination<ArticleResponse> getArticles(
            String sortOrder,
            String filterBy,
            String brandName,
            String categoryName,
            Integer page,
            Integer size)
    {

        Pagination<Article> paginationArticles =
                articleServicePort.getArticles(sortOrder, filterBy, brandName, categoryName, page, size);

        List<ArticleResponse> articleResponses = paginationArticles.getItems().stream()
                .map(articleResponseMapper::toResponse)
                .toList();

        return new Pagination<>(
                articleResponses,
                paginationArticles.getPageNo(),
                paginationArticles.getPageSize(),
                paginationArticles.getTotalItems(),
                paginationArticles.getTotalPages(),
                paginationArticles.getIsLastPage()
        );
    }

    @Override
    public Boolean updateArticleSupply(SupplyRequest supplyRequest) {
        Supply supply = supplyRequestMapper.toDomain(supplyRequest);
        return articleServicePort.updateArticleSupply(supply);
    }

}
