package com.pragma.emazon_stock.application.mappers.article;

import com.pragma.emazon_stock.application.dto.article.ArticleResponse;
import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.domain.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = Constants.SPRING_COMPONENT_MODEL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface ArticleResponseMapper {

    ArticleResponse toResponse(Article article);

    List<ArticleResponse> toResponse(List<Article> article);

}
