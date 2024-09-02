package com.pragma.emazon_stock.domain.spi;

import com.pragma.emazon_stock.domain.model.Article;

import java.util.List;

public interface ArticlePersistencePort {

    void saveArticle(Article article);

    Boolean checkIfArticleExists(String articleName);

    List<Article> getAllArticles();

}
