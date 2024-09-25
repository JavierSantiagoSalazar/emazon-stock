package com.pragma.emazon_stock.application.handler.article;

import com.pragma.emazon_stock.application.dto.article.ArticleRequest;
import com.pragma.emazon_stock.application.dto.article.ArticleResponse;
import com.pragma.emazon_stock.application.dto.article.SupplyRequest;
import com.pragma.emazon_stock.domain.model.Pagination;

import java.util.List;

public interface ArticleHandler {

    void createArticle(ArticleRequest articleRequest);

    Pagination<ArticleResponse> getArticles(
            String sortOrder,
            String filterBy,
            String brandName,
            String categoryName,
            Integer page,
            Integer size
    );

    Boolean updateArticleSupply(SupplyRequest supplyRequest);

    List<ArticleResponse> getArticlesByIds(List<Integer> articleIdList);

}
