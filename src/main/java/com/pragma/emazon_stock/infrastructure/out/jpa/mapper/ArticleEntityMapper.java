package com.pragma.emazon_stock.infrastructure.out.jpa.mapper;

import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.infrastructure.out.jpa.entity.ArticleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleEntityMapper {

    ArticleEntity toEntity(Article article);

}
