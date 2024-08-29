package com.pragma.emazon_stock.domain.api;

import com.pragma.emazon_stock.domain.model.Article;

public interface ArticleServicePort {

    void saveArticle(Article article);

    boolean checkIfArticleExists(String articleName);

}
