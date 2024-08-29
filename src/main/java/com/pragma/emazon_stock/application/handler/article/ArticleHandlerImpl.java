package com.pragma.emazon_stock.application.handler.article;

import com.pragma.emazon_stock.application.dto.article.ArticleRequest;
import com.pragma.emazon_stock.application.mappers.article.ArticleRequestMapper;
import com.pragma.emazon_stock.domain.api.ArticleServicePort;
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

}
