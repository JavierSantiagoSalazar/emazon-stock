package com.pragma.emazon_stock.application.mappers.category;

import com.pragma.emazon_stock.application.dto.category.CategoryResponse;
import com.pragma.emazon_stock.domain.model.Category;
import com.pragma.emazon_stock.domain.utils.Constants;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = Constants.SPRING_COMPONENT_MODEL,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface CategoryResponseMapper {

    CategoryResponse toResponse(Category category);

}
