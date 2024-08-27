package com.pragma.emazon_stock.application.mappers;

import com.pragma.emazon_stock.application.dto.CategoryResponse;
import com.pragma.emazon_stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface CategoryResponseMapper {

    CategoryResponse toResponse(Category category);

}
