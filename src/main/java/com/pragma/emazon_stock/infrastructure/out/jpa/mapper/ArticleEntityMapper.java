package com.pragma.emazon_stock.infrastructure.out.jpa.mapper;

import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.ArticleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleEntityMapper {

    ArticleEntity toEntity(Article article);

    @Mapping(target = "articleBrand.brandArticles", ignore = true)
    Article toDomain(ArticleEntity articleEntity);

    @Mapping(target = "articleBrand.brandArticles", ignore = true)
    List<Article> toArticleList(List<ArticleEntity> articleEntityList);

}
