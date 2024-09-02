package com.pragma.emazon_stock.domain.api;

import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.domain.model.Pagination;

public interface ArticleServicePort {

    void saveArticle(Article article);

    boolean checkIfArticleExists(String articleName);

    Pagination<Article> getArticles(String sortOrder, String filterBy, String brandName, String categoryName, Integer page, Integer size);

}
