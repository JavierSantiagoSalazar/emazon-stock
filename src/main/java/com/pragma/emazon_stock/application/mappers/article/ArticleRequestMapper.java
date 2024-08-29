package com.pragma.emazon_stock.application.mappers.article;

import com.pragma.emazon_stock.application.dto.article.ArticleRequest;
import com.pragma.emazon_stock.domain.model.Article;
import com.pragma.emazon_stock.domain.model.Category;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface ArticleRequestMapper {

    @Mapping(source = "articleCategories", target = "articleCategories", qualifiedByName = "mapCategories")
    Article toDomain(ArticleRequest articleRequest);

    @Named("mapCategories")
    default List<Category> mapCategories(List<String> articleCategories) {

        return articleCategories.stream()
                .map(name -> new Category(null, name, null))
                .toList();
    }

}
