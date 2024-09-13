package com.pragma.emazon_stock.infrastructure.out.jpa.adapter;

import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.domain.spi.ArticlePersistencePort;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.ArticleEntity;
import com.pragma.emazon_stock.infrastructure.out.jpa.mapper.ArticleEntityMapper;
import com.pragma.emazon_stock.infrastructure.out.jpa.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ArticleJpaAdapter implements ArticlePersistencePort {

    private final ArticleRepository articleRepository;
    private final ArticleEntityMapper articleEntityMapper;

    @Override
    public void saveArticle(Article article) {
        articleRepository.save(articleEntityMapper.toEntity(article));
    }

    @Override
    public Boolean checkIfArticleExists(String articleName) {
        return articleRepository.findByArticleName(articleName).isPresent();
    }

    @Override
    public List<Article> getAllArticles() {
        List<ArticleEntity> articleEntityList = articleRepository.findAll();
        return articleEntityMapper.toArticleList(articleEntityList);
    }

}
