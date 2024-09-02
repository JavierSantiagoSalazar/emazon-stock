package com.pragma.emazon_stock.application.handler.article;

import com.pragma.emazon_stock.application.dto.article.ArticleRequest;
import com.pragma.emazon_stock.application.dto.article.ArticleResponse;
import com.pragma.emazon_stock.domain.model.Pagination;

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
}
