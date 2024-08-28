package com.pragma.emazon_stock.application.mappers.category;

import com.pragma.emazon_stock.application.dto.CategoryRequest;
import com.pragma.emazon_stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface CategoryRequestMapper {

    Category toDomain(CategoryRequest categoryRequest);

}
