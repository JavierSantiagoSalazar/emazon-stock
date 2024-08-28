package com.pragma.emazon_stock.application.mappers.category;

import com.pragma.emazon_stock.application.dto.category.CategoryResponse;
import com.pragma.emazon_stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface CategoryResponseMapper {

    CategoryResponse toResponse(Category category);

}
