package com.pragma.emazon_stock.domain.spi;

import com.pragma.emazon_stock.domain.model.Article;

public interface ArticlePersistencePort {

    void saveArticle(Article article);

    Boolean checkIfArticleExists(String articleName);

}
