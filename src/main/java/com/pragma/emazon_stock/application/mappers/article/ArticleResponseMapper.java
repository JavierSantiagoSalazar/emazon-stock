package com.pragma.emazon_stock.application.mappers.article;

import com.pragma.emazon_stock.application.dto.article.ArticleResponse;
import com.pragma.emazon_stock.domain.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface ArticleResponseMapper {

    ArticleResponse toResponse(Article article);

}
